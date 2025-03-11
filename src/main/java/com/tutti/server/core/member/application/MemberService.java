package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.infrastructure.VerificationCodeRepository;
import com.tutti.server.core.member.payload.SignupRequest;
import com.tutti.server.core.member.utils.NicknameGenerator;
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
        // 5️. 닉네임 자동 생성
        String nickname = NicknameGenerator.generateNickname(request.email());

        // 6. 비밀번호 해싱 후 저장
        String encodedPassword = passwordEncoder.encode(request.password());
        Member member = Member.createEmailMember(request.email(), encodedPassword);

        member.verifyEmail();
        memberRepository.save(member);
        //추후 role 추가 예정 -> 고도화
    }
}