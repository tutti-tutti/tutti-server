package com.tutti.server.core.member.controller;

import com.tutti.server.core.member.dto.LoginRequest;
import com.tutti.server.core.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 이메일 로그인 API
    @PostMapping("/login/email")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        Map<String, String> tokens = authService.login(request);
        return ResponseEntity.ok(tokens);
    }
}
