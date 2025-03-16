package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.MemberAgreementMapping;
import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.member.domain.VerificationCode;
import com.tutti.server.core.member.infrastructure.MemberAgreementMappingRepository;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.infrastructure.TermsConditionsRepository;
import com.tutti.server.core.member.infrastructure.VerificationCodeRepository;
import com.tutti.server.core.member.payload.SignupRequest;
import com.tutti.server.core.member.payload.TermsAgreementRequest;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final TermsConditionsRepository termsConditionsRepository;
    private final MemberAgreementMappingRepository memberAgreementMappingRepository;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$");

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
            TermsConditions terms = termsConditionsRepository.findById(termsAgreement.termId())
                    .orElseThrow(() -> new DomainException(ExceptionType.TERMS_NOT_FOUND));

            MemberAgreementMapping agreementMapping = MemberAgreementMapping.builder()
                    .member(member)
                    .termsConditions(terms)
                    .isApproved(termsAgreement.agreed())
                    .build();

            memberAgreementMappingRepository.save(agreementMapping);
        }
    }
}
