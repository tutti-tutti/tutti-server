package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.EmailVerificationServiceImpl;
import com.tutti.server.core.member.payload.EmailVerificationConfirmRequest;
import java.util.Collections;
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
public class EmailVerificationApi implements EmailVerificationApiSpec {

    private final EmailVerificationServiceImpl emailVerificationServiceImpl;

    // 이메일 인증 코드 전송
    @Override
    @PostMapping("/email/verify")
    public ResponseEntity<Map<String, String>> requestEmailVerification(
            @RequestBody Map<String, String> request) {
        String email = request.get("email");
        String type = request.get("type");
        
        emailVerificationServiceImpl.sendVerificationEmail(email, type);
        return ResponseEntity.ok(Collections.singletonMap("message", "이메일 인증 코드가 발송되었습니다."));
    }

    @Override
    @PostMapping("/email/confirm")
    public ResponseEntity<Map<String, String>> verifyEmail(
            @RequestBody EmailVerificationConfirmRequest request) {
        emailVerificationServiceImpl.verifyEmail(request.email(), request.verificationCode());

        return ResponseEntity.ok(Collections.singletonMap("message", "이메일 인증이 완료되었습니다."));
    }
}
