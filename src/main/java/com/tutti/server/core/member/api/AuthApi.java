package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.AuthServiceImpl;
import com.tutti.server.core.member.payload.LoginRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthApi implements AuthApiSpec {

    private final AuthServiceImpl authServiceImpl;

    @Override
    public ResponseEntity<Map<String, String>> login(LoginRequest request) {
        Map<String, String> tokens = authServiceImpl.login(request);
        return ResponseEntity.ok(tokens);
    }
}
