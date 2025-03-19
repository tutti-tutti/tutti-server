package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원 API", description = "회원 관련 API")
public interface MemberApiSpec {

    @Operation(summary = "회원가입", description = "이메일을 이용한 회원가입을 진행합니다.")
    ResponseEntity<Map<String, String>> signup(SignupRequest request);
}
