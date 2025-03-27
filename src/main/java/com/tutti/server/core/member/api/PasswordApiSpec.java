package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.member.payload.PasswordChangeRequest;
import com.tutti.server.core.member.payload.PasswordResetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "비밀번호 관련 기능 API", description = "회원 비밀번호 변경, 리셋 API")
public interface PasswordApiSpec {

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "비밀번호 변경", description = "현재 로그인된 사용자의 비밀번호를 변경합니다.")
    ResponseEntity<Map<String, String>> changePassword(
            PasswordChangeRequest request,
            CustomUserDetails userDetails);

    @Operation(summary = "비밀번호 재설정", description = "이메일 인증을 통해 비밀번호를 재설정합니다.")
    ResponseEntity<Map<String, String>> resetPassword(
            @RequestBody @Valid PasswordResetRequest request);
}
