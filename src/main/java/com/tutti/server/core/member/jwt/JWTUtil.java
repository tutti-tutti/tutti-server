package com.tutti.server.core.member.jwt;

import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    public static final long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 10L * 1000; // 10시간
    public static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L; // 7일

    private SecretKey secretKey;

    public JWTUtil() {
        String secret = "7Jes6riw7JeQXzMy67CU7J207Yq4X+ydtOyDgeydmF/slYjsoITtlZxf7YKk";
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
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

    public String createJwt(String email, String memberStatus, Long expiredMs) {

        try {
            return Jwts.builder()
                    .claim("email", email)
                    .claim("memberStatus", memberStatus)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expiredMs))
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            throw new DomainException(ExceptionType.JWT_CREATION_FAILED);
        }
    }

    public String createRefreshToken(String email, Long expiredMs) {
        try {
            return Jwts.builder()
                    .claim("email", email)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expiredMs))
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            throw new DomainException(ExceptionType.JWT_CREATION_FAILED);
        }
    }


}
