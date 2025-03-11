package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.VerificationCode;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.infrastructure.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final JavaMailSender mailSender;
    private final MemberRepository memberRepository;

    private String generateVerificationCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    public void sendVerificationEmail(String email) {
        verificationCodeRepository.deleteByEmail(email); // 기존 인증번호 삭제 후 새로 발송

        String verificationCode = generateVerificationCode();
        VerificationCode newCode = new VerificationCode(email, verificationCode,
                LocalDateTime.now().plusMinutes(10));
        verificationCodeRepository.save(newCode);

        sendEmail(email, verificationCode);
    }

    private void sendEmail(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드: " + verificationCode + " (10분 이내 입력)");
        mailSender.send(message);
    }

    public boolean verifyEmail(String email, String code) {
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("인증 코드가 존재하지 않습니다."));

        if (verificationCode.isExpired() || !verificationCode.getVerificationCode().equals(code)) {
            throw new IllegalArgumentException("인증 코드가 올바르지 않거나 만료되었습니다.");


        }

        verificationCode.verify();
        verificationCodeRepository.save(verificationCode);

        //문제 상황 -> 현재 member 테이블에 정보 없음
//        Member member = memberRepository.findByEmail(email)
////                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자가 존재하지 않습니다."));
////
////        member.verifyEmail();
////        memberRepository.save(member);
        return true;
    }
}
