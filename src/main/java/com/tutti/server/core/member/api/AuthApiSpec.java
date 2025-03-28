package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.LoginRequest;
import com.tutti.server.core.member.payload.SignupRequest;
import com.tutti.server.core.member.payload.SocialLoginRequest;
import com.tutti.server.core.member.payload.WithdrawalRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "회원 기본 기능 API", description = "인증 관련 API")
public interface AuthApiSpec {

    @Operation(summary = "회원가입", description = "이메일을 이용한 회원가입을 진행합니다.")
    ResponseEntity<Map<String, String>> signup(SignupRequest request);

    @Operation(summary = "이메일 회원 로그인", description = "이메일과 비밀번호를 이용해 로그인을 진행합니다.")
    ResponseEntity<Map<String, String>> login(LoginRequest request);

    @Operation(summary = "소셜 회원 로그인", description = "소셜 사이트를 이용해 로그인을 진행합니다.")
    ResponseEntity<Map<String, String>> socialLogin(SocialLoginRequest request);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "회원 로그아웃", description = "클라이언트에서 JWT 토큰을 삭제하면 로그아웃이 완료됩니다.")
    ResponseEntity<String> logout(String authorization,
            UserDetails userDetails);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "로그인 회원 AccessToken 갱신", description = "RefreshToken을 이용하여 AccessToken을 갱신합니다.")
    ResponseEntity<Map<String, String>> updateAccessToken(HttpServletRequest request);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "이메일 회원 탈퇴", description = "비밀번호와 RefreshToken을 통해 본인 인증 후 회원 탈퇴를 진행합니다.")
    ResponseEntity<String> withdraw(String authorization, WithdrawalRequest request);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "소셜 로그인 회원 탈퇴", description = "소셜 로그인 회원의 계정을 삭제합니다.")
    ResponseEntity<Map<String, String>> withdrawSocial(String authorization);
}
