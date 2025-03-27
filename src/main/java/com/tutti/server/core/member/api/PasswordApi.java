package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.member.application.EmailVerificationServiceImpl;
import com.tutti.server.core.member.application.PasswordChangeServiceImpl;
import com.tutti.server.core.member.application.PasswordResetServiceImpl;
import com.tutti.server.core.member.payload.PasswordChangeRequest;
import com.tutti.server.core.member.payload.PasswordResetRequest;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class PasswordApi implements PasswordApiSpec {

    private final PasswordChangeServiceImpl passwordChangeServiceImpl;
    private final EmailVerificationServiceImpl emailVerificationServiceImpl;
    private final PasswordResetServiceImpl passwordResetServiceImpl;

    @Override
    @PostMapping("/password/change")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestBody @Valid PasswordChangeRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        long memberId = userDetails.getMemberId();
        passwordChangeServiceImpl.changePassword(memberId, request);

        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }

    @Override
    @PostMapping("/password/reset")
    public ResponseEntity<Map<String, String>> resetPassword(
            @RequestBody @Valid PasswordResetRequest request) {
        String email = request.email();
        String password = request.newPassword();
        String passwordConfirm = request.newPasswordConfirm();

        if (!password.equals(passwordConfirm)) {
            throw new DomainException(ExceptionType.PASSWORD_MISMATCH);
        }

        passwordResetServiceImpl.resetPassword(email, password);

        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }
}
