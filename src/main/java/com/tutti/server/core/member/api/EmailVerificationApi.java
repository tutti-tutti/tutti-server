package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.EmailVerificationService;
import com.tutti.server.core.member.payload.EmailVerificationRequest;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/members")
@RequiredArgsConstructor
public class EmailVerificationApi {

    private final EmailVerificationService emailVerificationService;

    // 이메일 인증 코드 전송
    @PostMapping("/email/verify")
    public ResponseEntity<?> requestEmailVerification(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "이메일은 필수 입력 항목입니다."));
        }

        emailVerificationService.sendVerificationEmail(email);
        return ResponseEntity.ok(Collections.singletonMap("message", "이메일 인증 코드가 발송되었습니다."));
    }

    // 사용자가 입력한 인증 코드 검증
    @PostMapping("/email/confirm")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailVerificationRequest request) {
        boolean success = emailVerificationService.verifyEmail(request.email(),
                request.verificationCode());

        if (!success) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "인증 코드가 올바르지 않거나 만료되었습니다."));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "이메일 인증이 완료되었습니다."));
    }
}
