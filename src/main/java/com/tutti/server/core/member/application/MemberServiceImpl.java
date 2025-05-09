package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.MemberAgreementMapping;
import com.tutti.server.core.member.domain.MemberCategoryScore;
import com.tutti.server.core.member.domain.MemberTagScore;
import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.member.domain.VerificationCode;
import com.tutti.server.core.member.infrastructure.MemberAgreementMappingRepository;
import com.tutti.server.core.member.infrastructure.MemberCategoryScoreRepository;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.infrastructure.MemberTagScoreRepository;
import com.tutti.server.core.member.infrastructure.TermsConditionsRepository;
import com.tutti.server.core.member.infrastructure.VerificationCodeRepository;
import com.tutti.server.core.member.payload.SignupRequest;
import com.tutti.server.core.member.payload.TermsAgreementRequest;
import com.tutti.server.core.product.infrastructure.ProductCategoryRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import com.tutti.server.core.tag.infrastructure.TagRepository;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberServiceSpec {

    private static final List<Long> VALUE_TAG_IDS = List.of(6L, 10L, 11L, 5L, 9L); // 가성비
    private static final List<Long> QUALITY_TAG_IDS = List.of(7L, 13L, 17L, 15L, 14L, 16L, 20L, 34L,
            31L, 32L, 33L); // 품질
    private static final List<Long> TREND_TAG_IDS = List.of(2L, 22L, 8L, 12L, 26L, 29L); // 트렌드

    private final MemberRepository memberRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final TermsConditionsRepository termsConditionsRepository;
    private final MemberAgreementMappingRepository memberAgreementMappingRepository;
    private final MemberCategoryScoreRepository memberCategoryScoreRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final MemberTagScoreRepository memberTagScoreRepository;
    private final TagRepository tagRepository;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$");

    @Override
    public void signup(SignupRequest request) {

        // 1. 필수 약관 조회
        List<TermsConditions> requiredTerms = termsConditionsRepository.findAll().stream()
                .filter(TermsConditions::isRequired) // 필수 약관만 필터링
                .toList();

        // 2. 사용자가 제공한 약관 동의 데이터
        Map<Long, Boolean> userAgreements = request.termsAgreement().stream()
                .collect(Collectors.toMap(TermsAgreementRequest::termId,
                        TermsAgreementRequest::agreed));

        // 3. 필수 약관 동의 여부 검증
        for (TermsConditions terms : requiredTerms) {
            if (!userAgreements.getOrDefault(terms.getId(), false)) {
                throw new DomainException(ExceptionType.REQUIRED_TERMS_NOT_AGREED);
            }
        }

        // 회원 정보 저장
        // 1. 필수 필드 검증
        if (request.email() == null || request.password() == null
                || request.passwordConfirm() == null) {
            throw new DomainException(ExceptionType.MISSING_REQUIRED_FIELD);
        }

        // 2. 비밀번호 일치 확인
        if (!request.password().equals(request.passwordConfirm())) {
            throw new DomainException(ExceptionType.PASSWORD_MISMATCH);
        }

        // 3. 비밀번호 길이 확인
        if (request.password().length() < 8) {
            throw new DomainException(ExceptionType.PASSWORD_TOO_SHORT);
        }

        // 4. 복잡도 확인 (정규식 사용)
        if (!PASSWORD_PATTERN.matcher(request.password()).matches()) {
            throw new DomainException(ExceptionType.PASSWORD_COMPLEXITY_NOT_MET);
        }

        // 5. 이메일 중복 확인
        if (memberRepository.existsByEmail(request.email())) {
            throw new DomainException(ExceptionType.EMAIL_ALREADY_EXISTS);
        }

        // 6. 이메일 인증 여부 확인
        // 5. 이메일 인증 여부 확인 (중복 제거)
        var verificationCode = verificationCodeRepository.findByEmail(request.email())
                .filter(VerificationCode::isVerified)
                .orElseThrow(() -> new DomainException(ExceptionType.EMAIL_NOT_VERIFIED));

        // 7. 비밀번호 해싱 후 저장
        String encodedPassword = passwordEncoder.encode(request.password());
        Member member = Member.createEmailMember(request.email(), encodedPassword);

        member.verifyEmail();
        memberRepository.save(member);

        // 약관 동의 정보 저장
        for (TermsAgreementRequest termsAgreement : request.termsAgreement()) {
            TermsConditions terms = termsConditionsRepository.findOne(termsAgreement.termId());

            MemberAgreementMapping agreementMapping = MemberAgreementMapping.builder()
                    .member(member)
                    .termsConditions(terms)
                    .isApproved(termsAgreement.agreed())
                    .build();

            memberAgreementMappingRepository.save(agreementMapping);
        }

        for (Long categoryId : request.preferredCategoryIds()) {
            var category = productCategoryRepository.findOne(categoryId);

            memberCategoryScoreRepository.save(
                    MemberCategoryScore.builder()
                            .member(member)
                            .category(category)
                            .score(10)
                            .build()
            );
        }

        List<Long> tagIds = switch (request.shoppingValue()) {
            case VALUE -> VALUE_TAG_IDS;
            case QUALITY -> QUALITY_TAG_IDS;
            case TREND -> TREND_TAG_IDS;
        };

        for (Long tagId : tagIds) {
            memberTagScoreRepository.save(
                    MemberTagScore.builder()
                            .member(member)
                            .tag(tagRepository.findOne(tagId))
                            .score(10)
                            .build()
            );
        }
    }
}
