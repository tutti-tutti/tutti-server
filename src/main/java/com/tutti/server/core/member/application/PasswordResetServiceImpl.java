package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
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

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");

    @Override
    public void resetPassword(String email, String newPassword) {
        // 1. 사용자 존재 여부 확인
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));

        // 2. 비밀번호 유효성 검증
        if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
            throw new DomainException(ExceptionType.INVALID_PASSWORD_FORMAT);
        }

        // 3. 비밀번호 업데이트 및 저장
        member.updatePassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }
}
