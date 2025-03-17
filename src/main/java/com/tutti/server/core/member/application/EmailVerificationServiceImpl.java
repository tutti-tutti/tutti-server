package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.VerificationCode;
import com.tutti.server.core.member.infrastructure.VerificationCodeRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
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
public class EmailVerificationServiceImpl implements EmailVerificationServiceSpec {

    private final VerificationCodeRepository verificationCodeRepository;
    private final JavaMailSender mailSender;

    private String generateVerificationCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    @Override
    public void sendVerificationEmail(String email) {
        validateEmailFormat(email); // 이메일 형식 검증

        verificationCodeRepository.findByEmail(email).ifPresent(existingCode -> {
            if (existingCode.isVerified()) {
                throw new DomainException(ExceptionType.EMAIL_ALREADY_VERIFIED);
            }
        });

        verificationCodeRepository.deleteByEmail(email); // 기존 인증번호 삭제 후 새로 발송

        String verificationCode = generateVerificationCode();
        VerificationCode newCode = new VerificationCode(email, verificationCode,
                LocalDateTime.now().plusMinutes(10));
        verificationCodeRepository.save(newCode);

        sendEmail(email, verificationCode);
    }

    private void validateEmailFormat(String email) {
        if (email == null || email.isBlank() || !email.matches(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new DomainException(ExceptionType.INVALID_EMAIL_FORMAT);
        }
    }

    private void sendEmail(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드: " + verificationCode + " (10분 이내 입력)");
        mailSender.send(message);
    }

    @Override
    public void verifyEmail(String email, String code) {
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException(ExceptionType.INVALID_VERIFICATION_CODE));

        if (verificationCode.isVerified()) {
            throw new DomainException(ExceptionType.EMAIL_ALREADY_VERIFIED);
        }

        if (!verificationCode.getVerificationCode().equals(code)) {
            throw new DomainException(ExceptionType.INVALID_VERIFICATION_CODE);
        }

        if (verificationCode.isExpired()) {
            throw new DomainException(ExceptionType.INVALID_VERIFICATION_CODE);
        }

        verificationCode.verify();
        verificationCodeRepository.save(verificationCode);

    }
}
