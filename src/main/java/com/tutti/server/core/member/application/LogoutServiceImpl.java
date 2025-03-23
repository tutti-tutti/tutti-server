package com.tutti.server.core.member.application;

import com.tutti.server.core.member.jwt.JWTUtil;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import io.jsonwebtoken.Jwts;
import java.time.Duration;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutServiceSpec {

    private final JWTUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void logout(String token) {
        if (jwtUtil.isExpired(token)) {
            throw new DomainException(ExceptionType.TOKEN_EXPIRED);
        }
        if (!jwtUtil.isRefreshToken(token)) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }

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
