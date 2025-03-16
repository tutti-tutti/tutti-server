package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.EmailVerificationService;
import com.tutti.server.core.member.application.PasswordResetService;
import com.tutti.server.core.member.payload.PasswordResetRequest;
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
public class PasswordResetApi {

    private final EmailVerificationService emailVerificationService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
        String email = request.email();
        String verificationCode = request.verificationCode();
        String password = request.newPassword();
        // 1. 이메일 인증 코드 확인
        emailVerificationService.verifyEmail(email, verificationCode);

        // 2. 비밀번호 재설정 요청
        passwordResetService.resetPassword(email, password);

        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }
}
