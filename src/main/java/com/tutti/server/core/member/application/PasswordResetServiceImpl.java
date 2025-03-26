package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.VerificationCode;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.infrastructure.VerificationCodeRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetServiceSpec {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");

    @Override
    public void resetPassword(String email, String newPassword) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));

        VerificationCode code = verificationCodeRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException(ExceptionType.INVALID_VERIFICATION_CODE));

        if (!code.isVerified()) {
            throw new DomainException(ExceptionType.EMAIL_NOT_VERIFIED);
        }

        if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
            throw new DomainException(ExceptionType.INVALID_PASSWORD_FORMAT);
        }

        member.updatePassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);

        code.expire();
        verificationCodeRepository.save(code);
    }
}
