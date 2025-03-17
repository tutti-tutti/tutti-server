package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "인증 API", description = "로그인 및 인증 관련 API")
@RequestMapping("members")
public interface AuthApiSpec {

    @Operation(summary = "이메일 로그인", description = "이메일과 비밀번호를 이용해 로그인을 진행합니다.")
    @PostMapping("/login/email")
    ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequest request);
}
