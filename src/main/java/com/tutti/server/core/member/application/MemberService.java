package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.MemberAgreementMapping;
import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.member.infrastructure.MemberAgreementMappingRepository;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.infrastructure.TermsConditionsRepository;
import com.tutti.server.core.member.infrastructure.VerificationCodeRepository;
import com.tutti.server.core.member.payload.SignupRequest;
import com.tutti.server.core.member.payload.TermsAgreementRequest;
import java.util.List;
import java.util.Map;
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
                throw new IllegalArgumentException(
                        "필수 약관(" + terms.getTermsType().getDescription() + ")에 동의해야 합니다.");
            }
        }
        // 회원 정보 저장
        // 1. 필수 필드 검증
        if (request.email() == null || request.password() == null
                || request.passwordConfirm() == null) {
            throw new IllegalArgumentException("이메일, 비밀번호 및 비밀번호 확인은 필수 입력 항목입니다.");
        }

        // 2. 비밀번호 일치 확인
        if (!request.password().equals(request.passwordConfirm())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 3. 이메일 중복 확인
        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 4. 이메일 인증 여부 확인
        var verificationCode = verificationCodeRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일 인증을 완료해야 회원가입이 가능합니다."));

        if (!verificationCode.isVerified()) {
            throw new IllegalArgumentException("이메일 인증을 완료해야 회원가입이 가능합니다.");
        }

        // 5. 비밀번호 해싱 후 저장
        String encodedPassword = passwordEncoder.encode(request.password());
        Member member = Member.createEmailMember(request.email(), encodedPassword);

        member.verifyEmail();
        memberRepository.save(member);

        // 약관 동의 정보 저장
        for (TermsAgreementRequest termsAgreement : request.termsAgreement()) {
            TermsConditions terms = termsConditionsRepository.findById(termsAgreement.termId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "유효하지 않은 약관 ID: " + termsAgreement.termId()));

            MemberAgreementMapping agreementMapping = MemberAgreementMapping.builder()
                    .member(member)
                    .termsConditions(terms)
                    .isApproved(termsAgreement.agreed())
                    .build();

            memberAgreementMappingRepository.save(agreementMapping);
        }
    }
}
