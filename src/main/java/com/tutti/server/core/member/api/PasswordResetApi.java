package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.EmailVerificationServiceImpl;
import com.tutti.server.core.member.application.PasswordResetServiceImpl;
import com.tutti.server.core.member.payload.PasswordResetRequest;
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
public class PasswordResetApi implements PasswordResetApiSpec {

    private final EmailVerificationServiceImpl emailVerificationServiceImpl;
    private final PasswordResetServiceImpl passwordResetServiceImpl;

    @Override
    @PostMapping("/password/reset")
    public ResponseEntity<Map<String, String>> resetPassword(
        @RequestBody @Valid PasswordResetRequest request) {
        String email = request.email();
        String verificationCode = request.verificationCode();
        String password = request.newPassword();
        // 1. 이메일 인증 코드 확인
        emailVerificationServiceImpl.verifyEmail(email, verificationCode);

        // 2. 비밀번호 재설정 요청
        passwordResetServiceImpl.resetPassword(email, password);

        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }
}
