package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.PasswordChangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "비밀번호 변경 API", description = "회원 비밀번호 변경 API")
public interface PasswordChangeApiSpec {

    @Operation(summary = "비밀번호 변경", description = "현재 로그인된 사용자의 비밀번호를 변경합니다.")
    ResponseEntity<Map<String, String>> changePassword(
        PasswordChangeRequest request,
        UserDetails userDetails);
}
