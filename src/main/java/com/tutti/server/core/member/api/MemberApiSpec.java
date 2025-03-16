package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "회원 API", description = "회원 관련 API")
@RequestMapping("api/v1/members")
public interface MemberApiSpec {

    @Operation(summary = "회원가입", description = "이메일을 이용한 회원가입을 진행합니다.")
    @PostMapping("/signup/email")
    ResponseEntity<Map<String, String>> signup(@RequestBody @Valid SignupRequest request);
}
