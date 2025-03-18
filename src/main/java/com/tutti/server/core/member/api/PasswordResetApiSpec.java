package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.PasswordResetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "비밀번호 재설정 API", description = "회원 비밀번호 재설정 API")
public interface PasswordResetApiSpec {

    @Operation(summary = "비밀번호 재설정", description = "이메일 인증을 통해 비밀번호를 재설정합니다.")
    ResponseEntity<Map<String, String>> resetPassword(
        @RequestBody @Valid PasswordResetRequest request);
}
