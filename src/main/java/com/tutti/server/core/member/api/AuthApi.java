package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.AuthService;
import com.tutti.server.core.member.payload.LoginRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    // 이메일 로그인 API
    @PostMapping("/login/email")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        Map<String, String> tokens = authService.login(request);
        return ResponseEntity.ok(tokens);
    }
}
