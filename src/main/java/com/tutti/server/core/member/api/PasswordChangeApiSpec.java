package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.PasswordChangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "비밀번호 변경 API", description = "회원 비밀번호 변경 API")
@RequestMapping("members")
public interface PasswordChangeApiSpec {

    @Operation(summary = "비밀번호 변경", description = "현재 로그인된 사용자의 비밀번호를 변경합니다.")
    @PostMapping("/password/change")
    ResponseEntity<Map<String, String>> changePassword(
            @RequestBody @Valid PasswordChangeRequest request,
            @AuthenticationPrincipal UserDetails userDetails);
}
