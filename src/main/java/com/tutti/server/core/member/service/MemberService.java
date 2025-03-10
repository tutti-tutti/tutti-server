package com.tutti.server.core.member.service;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.dto.SignupRequest;
import com.tutti.server.core.member.repository.MemberRepository;
import com.tutti.server.core.member.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {
        // 1. 필수 필드 검증
        if (request.getEmail() == null || request.getPassword() == null
                || request.getPasswordConfirm() == null) {
            throw new IllegalArgumentException("이메일, 비밀번호 및 비밀번호 확인은 필수 입력 항목입니다.");
        }

        // 2. 비밀번호 일치 확인
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 3. 이메일 중복 확인
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 4. 이메일 인증 여부 확인
        var verificationCode = verificationCodeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 인증을 완료해야 회원가입이 가능합니다."));

        if (!verificationCode.isVerified()) {
            throw new IllegalArgumentException("이메일 인증을 완료해야 회원가입이 가능합니다.");
        }

        // 5. 비밀번호 해싱 후 저장
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.createEmailMember(request.getEmail(), encodedPassword);

        member.verifyEmail();
        memberRepository.save(member);
        //추후 role 추가 예정 -> 고도화
    }
}