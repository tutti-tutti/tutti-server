package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.EmailVerificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원 이메일 인증 API", description = "이메일 인증 관련 API")
public interface EmailVerificationApiSpec {

    @Operation(summary = "이메일 인증 코드 전송", description = "사용자의 이메일로 인증 코드를 전송합니다.")
    ResponseEntity<Map<String, String>> requestEmailVerification(
        Map<String, String> request);

    @Operation(summary = "이메일 인증 코드 확인", description = "사용자가 입력한 인증 코드가 올바른지 확인합니다.")
    ResponseEntity<Map<String, String>> verifyEmail(EmailVerificationRequest request);
}
