package com.tutti.server.core.member.jwt;

import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    public static final long ACCESS_TOKEN_EXPIRATION =
            60 * 60L * 1000L; // (60초 * 60분 * 1000밀리초 = 1시간/ 수정 및 픽스 예정)
    public static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L; // 7일

    private SecretKey secretKey;

    public JWTUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String getEmail(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("email", String.class);
        } catch (ExpiredJwtException e) {
            throw new DomainException(ExceptionType.TOKEN_EXPIRED);
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        } catch (io.jsonwebtoken.security.SecurityException e) {
            throw new DomainException(ExceptionType.INVALID_JWT_SIGNATURE);
        }
    }

    public Long getMemberId(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("memberId", Long.class);
        } catch (ExpiredJwtException e) {
            throw new DomainException(ExceptionType.TOKEN_EXPIRED);
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        } catch (io.jsonwebtoken.security.SecurityException e) {
            throw new DomainException(ExceptionType.INVALID_JWT_SIGNATURE);
        }
    }

    public Boolean isExpired(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(new Date());
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            return true;
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }
    }

    public String createJwt(Long memberId, String email, String memberStatus, Long expiredMs) {
        try {
            return Jwts.builder()
                    .claim("memberId", memberId) // memberId 추가
                    .claim("email", email)
                    .claim("memberStatus", memberStatus)
                    .claim("tokenType", "access")
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expiredMs))
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            throw new DomainException(ExceptionType.JWT_CREATION_FAILED);
        }
    }

    public String createRefreshToken(Long memberId, String email, String memberStatus,
            Long expiredMs) {
        try {
            return Jwts.builder()
                    .claim("memberId", memberId) // memberId 추가
                    .claim("email", email)
                    .claim("memberStatus", memberStatus)
                    .claim("tokenType", "refresh")
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expiredMs))
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            throw new DomainException(ExceptionType.JWT_CREATION_FAILED);
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            String tokenType = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("tokenType", String.class);

            return "refresh".equals(tokenType);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(refreshToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw new DomainException(ExceptionType.TOKEN_EXPIRED);
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();

            String tokenType = claims.get("tokenType", String.class);

            if (!"access".equals(tokenType)) {
                throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
            }

            return true;
        } catch (ExpiredJwtException e) {
            throw new DomainException(ExceptionType.TOKEN_EXPIRED);
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}

