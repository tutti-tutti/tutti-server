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

        emailVerificationService.sendVerificationEmail(email);
        return ResponseEntity.ok(Collections.singletonMap("message", "이메일 인증 코드가 발송되었습니다."));
    }

    @PostMapping("/email/confirm")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailVerificationRequest request) {
        emailVerificationService.verifyEmail(request.email(),
                request.verificationCode());

        return ResponseEntity.ok(Collections.singletonMap("message", "이메일 인증이 완료되었습니다."));
    }
}
