package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.EmailVerificationServiceImpl;
import com.tutti.server.core.member.payload.EmailVerificationRequest;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailVerificationApi implements EmailVerificationApiSpec {

    private final EmailVerificationServiceImpl emailVerificationServiceImpl;

    // 이메일 인증 코드 전송
    @Override
    public ResponseEntity<Map<String, String>> requestEmailVerification(
            Map<String, String> request) {
        String email = request.get("email");

        emailVerificationServiceImpl.sendVerificationEmail(email);
        return ResponseEntity.ok(Collections.singletonMap("message", "이메일 인증 코드가 발송되었습니다."));
    }

    @Override
    public ResponseEntity<Map<String, String>> verifyEmail(EmailVerificationRequest request) {
        emailVerificationServiceImpl.verifyEmail(request.email(), request.verificationCode());

        return ResponseEntity.ok(Collections.singletonMap("message", "이메일 인증이 완료되었습니다."));
    }
}
