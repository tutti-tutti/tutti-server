package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.MemberStatus;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.jwt.JWTUtil;
import com.tutti.server.core.member.payload.LoginRequest;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import io.jsonwebtoken.Jwts;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthServiceSpec {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

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
        String accessToken = jwtUtil.createJwt(
                member.getId(),
                member.getEmail(),
                member.getMemberStatus().name(),
                JWTUtil.ACCESS_TOKEN_EXPIRATION
        );

        String refreshToken = jwtUtil.createRefreshToken(
                member.getId(),
                member.getEmail(),
                member.getMemberStatus().name(),
                JWTUtil.REFRESH_TOKEN_EXPIRATION
        );

        return Map.of(
                "access_token", accessToken,
                "refresh_token", refreshToken
        );
    }

    @Override
    public Map<String, String> updateAccessToken(String refreshToken) {
        validateRefreshToken(refreshToken);

        long memberId = jwtUtil.getMemberId(refreshToken);
        String email = jwtUtil.getEmail(refreshToken);

        String newAccessToken = jwtUtil.createJwt(
                memberId,
                email,
                "ACTIVE",
                JWTUtil.ACCESS_TOKEN_EXPIRATION
        );

        return Map.of("access_token", newAccessToken);
    }

    @Override
    public void withdrawMember(String refreshToken, String password) {
        validateRefreshToken(refreshToken);

        String email = jwtUtil.getEmail(refreshToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new DomainException(ExceptionType.LOGIN_PASSWORD_MISMATCH);
        }

        member.withdraw();
        memberRepository.save(member);

        blacklistRefreshToken(refreshToken);
    }

    private void validateRefreshToken(String token) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(token))) {
            throw new DomainException(ExceptionType.TOKEN_LOGGED_OUT);
        }

        if (!jwtUtil.isRefreshToken(token) || !jwtUtil.validateRefreshToken(token)) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }

        if (jwtUtil.isExpired(token)) {
            throw new DomainException(ExceptionType.TOKEN_EXPIRED);
        }
    }

    private void blacklistRefreshToken(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(jwtUtil.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        long expireInMs = expiration.getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set(token, "logout", Duration.ofMillis(expireInMs));
    }
}
