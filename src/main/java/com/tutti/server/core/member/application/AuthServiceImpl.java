package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.MemberStatus;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.payload.LoginRequest;
import com.tutti.server.core.member.security.JWTUtil;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthServiceSpec {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public Map<String, String> login(LoginRequest request) {
        // 1. 이메일 존재 여부 확인
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));

        // 2. 이메일 인증 여부 확인
        if (!member.isEmailVerified()) {
            throw new DomainException(ExceptionType.EMAIL_NOT_VERIFIED);
        }

        // 3. 계정 상태 확인
        if (member.getMemberStatus() == MemberStatus.WITHDRAWN) {
            throw new DomainException(ExceptionType.ACCOUNT_WITHDRAWN);
        }

        if (member.getMemberStatus() == MemberStatus.BANNED) {
            throw new DomainException(ExceptionType.ACCOUNT_BANNED);
        }

        // 4. 비밀번호 검증
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new DomainException(ExceptionType.LOGIN_PASSWORD_MISMATCH);
        }

        // 5. JWT 토큰 생성
        String accessToken = jwtUtil.createJwt(member.getEmail(), member.getMemberStatus().name(),
                JWTUtil.ACCESS_TOKEN_EXPIRATION);
        String refreshToken = jwtUtil.createRefreshToken(member.getEmail(),
                JWTUtil.REFRESH_TOKEN_EXPIRATION);

        return Map.of(
                "access_token", accessToken,
                "refresh_token", refreshToken
        );
    }
}

