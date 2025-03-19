package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@Tag(name = "인증 API", description = "로그인 및 인증 관련 API")
public interface AuthApiSpec {

    @Operation(summary = "이메일 로그인", description = "이메일과 비밀번호를 이용해 로그인을 진행합니다.")
    ResponseEntity<Map<String, String>> login(LoginRequest request);
}
