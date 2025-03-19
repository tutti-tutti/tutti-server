package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.SignupRequest;
import com.tutti.server.core.member.payload.SocialLoginRequest;
import com.tutti.server.core.member.payload.SocialSignupRequest;
import com.tutti.server.core.member.payload.TermsAgreementRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "회원 API", description = "회원 관련 API")
@RequestMapping("members")
public interface MemberApiSpec {

    @Operation(summary = "이메일 회원가입", description = "이메일을 이용한 회원가입을 진행합니다.")
    @PostMapping("/signup/email")
    ResponseEntity<Map<String, String>> signup(@RequestBody @Valid SignupRequest request);

    @Operation(summary = "소셜 회원가입", description = "소셜 로그인으로 회원가입을 진행합니다.")
    @PostMapping("/signup/social")
    ResponseEntity<Map<String, String>> socialSignup(
            @RequestBody @Valid SocialSignupRequest request);

    @Operation(summary = "소셜 로그인", description = "소셜 로그인으로 JWT 발급을 진행합니다.")
    @PostMapping("/login/social")
    ResponseEntity<Map<String, String>> socialLogin(@RequestBody @Valid SocialLoginRequest request);

    @Operation(summary = "소셜 회원가입 약관 동의", description = "소셜 회원가입 후 약관 동의를 완료합니다.")
    @PostMapping("/signup/social/complete")
    ResponseEntity<Map<String, String>> completeSocialSignup(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid List<TermsAgreementRequest> request);
}

