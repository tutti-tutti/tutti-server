package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.LogoutServiceImpl;
import com.tutti.server.core.member.jwt.JWTUtil;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class LogoutApi implements LogoutApiSpec {

    private final JWTUtil jwtUtil;
    private final LogoutServiceImpl logoutServiceImpl;

    @Override
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @AuthenticationPrincipal UserDetails userDetails) {
        // Authorization 헤더가 없거나 Bearer 토큰이 아닌 경우
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }

        String token = authorization.replace("Bearer ", "");
        logoutServiceImpl.logout(token);

        return ResponseEntity.ok("{\"message\": \"성공적으로 로그아웃되었습니다.\"}");
    }
}
