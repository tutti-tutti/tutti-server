package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.payload.PasswordChangeRequest;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordChangeServiceImpl implements PasswordChangeServiceSpec {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void changePassword(String email, PasswordChangeRequest request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(request.currentPassword(), member.getPassword())) {
            throw new DomainException(ExceptionType.LOGIN_PASSWORD_MISMATCH);
        }

        // 새 비밀번호 & 확인 비밀번호 일치 여부 확인
        if (!request.newPassword().equals(request.newPasswordConfirm())) {
            throw new DomainException(ExceptionType.PASSWORD_MISMATCH);
        }

        // 비밀번호 정책 검증
        if (!isValidPassword(request.newPassword())) {
            throw new DomainException(ExceptionType.INVALID_PASSWORD_FORMAT);
        }

        // 새 비밀번호가 기존 비밀번호와 동일한지 확인
        if (passwordEncoder.matches(request.newPassword(), member.getPassword())) {
            throw new DomainException(ExceptionType.PASSWORD_SAME_AS_OLD);
        }

        member.updatePassword(passwordEncoder.encode(request.newPassword()));
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";
        return password.matches(regex);
    }
}

