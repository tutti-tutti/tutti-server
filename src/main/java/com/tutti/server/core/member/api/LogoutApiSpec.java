package com.tutti.server.core.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "로그아웃 API", description = "JWT 로그아웃을 처리하는 API")
@RequestMapping("members")
public interface LogoutApiSpec {

    @Operation(summary = "JWT 로그아웃", description = "클라이언트에서 JWT 토큰을 삭제하면 로그아웃이 완료됩니다.")
    @PostMapping("/logout")
    ResponseEntity<String> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @AuthenticationPrincipal UserDetails userDetails);
}
