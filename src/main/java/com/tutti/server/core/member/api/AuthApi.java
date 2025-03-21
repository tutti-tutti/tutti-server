package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.AuthServiceImpl;
import com.tutti.server.core.member.payload.LoginRequest;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class AuthApi implements AuthApiSpec {

    private final AuthServiceImpl authServiceImpl;

    @Override
    @PostMapping("/login/email")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequest request) {
        Map<String, String> tokens = authServiceImpl.login(request);
        return ResponseEntity.ok(tokens);
    }

    @Override
    @PostMapping("/update-token")
    public ResponseEntity<Map<String, String>> updateAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new DomainException(ExceptionType.MISSING_AUTH_HEADER);
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }

        String refreshToken = authorizationHeader.replace("Bearer ", "");
        Map<String, String> tokens = authServiceImpl.updateAccessToken(refreshToken);

        return ResponseEntity.ok(tokens);
    }
}
