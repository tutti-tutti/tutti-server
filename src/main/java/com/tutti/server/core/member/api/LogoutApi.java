package com.tutti.server.core.member.api;

import com.tutti.server.core.member.security.JWTUtil;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogoutApi implements LogoutApiSpec {

    private final JWTUtil jwtUtil;

    @Override
    public ResponseEntity<String> logout(String authorization, UserDetails userDetails) {
        // Authorization 헤더가 없거나 Bearer 토큰이 아닌 경우
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }

        String token = authorization.replace("Bearer ", "");

        // 토큰이 만료된 경우
        if (jwtUtil.isExpired(token)) {
            throw new DomainException(ExceptionType.TOKEN_EXPIRED);
        }

        return ResponseEntity.ok("{\"message\": \"성공적으로 로그아웃되었습니다.\"}");
    }
}
