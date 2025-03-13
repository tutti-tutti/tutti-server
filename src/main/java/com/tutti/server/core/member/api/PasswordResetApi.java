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
        try {
            emailVerificationService.verifyEmail(email, verificationCode);

            if (!passwordResetService.isValidPassword(password)) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "비밀번호는 최소 8자 이상이며, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."));
            }
            passwordResetService.updatePassword(email, password);

            return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
