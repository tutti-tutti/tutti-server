package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.AuthServiceImpl;
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
}
