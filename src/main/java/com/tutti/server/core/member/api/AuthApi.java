package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.AuthServiceImpl;
import com.tutti.server.core.member.application.LogoutServiceImpl;
import com.tutti.server.core.member.application.MemberServiceImpl;
import com.tutti.server.core.member.payload.LoginRequest;
import com.tutti.server.core.member.payload.SignupRequest;
import com.tutti.server.core.member.payload.SocialLoginRequest;
import com.tutti.server.core.member.payload.WithdrawalRequest;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class AuthApi implements AuthApiSpec {

    private final AuthServiceImpl authServiceImpl;
    private final LogoutServiceImpl logoutServiceImpl;
    private final MemberServiceImpl memberServiceImpl;

    @Override
    @PostMapping("/signup/email")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Valid SignupRequest request) {
        memberServiceImpl.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "회원가입이 완료되었습니다."));
    }

    @Override
    @PostMapping("/login/email")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequest request) {
        Map<String, String> tokens = authServiceImpl.login(request);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/social")
    public ResponseEntity<Map<String, String>> socialLogin(
            @RequestBody @Valid SocialLoginRequest request) {
        Map<String, String> tokens = authServiceImpl.socialLogin(request);
        return ResponseEntity.ok(tokens);
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new DomainException(ExceptionType.MISSING_AUTH_HEADER);
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }

        String token = authorizationHeader.replace("Bearer ", "");
        logoutServiceImpl.logout(token);

        return ResponseEntity.ok("{\"message\": \"성공적으로 로그아웃되었습니다.\"}");
    }

    @Override
    @PostMapping("/update-token")
    public ResponseEntity<Map<String, String>> updateAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new DomainException(ExceptionType.MISSING_AUTH_HEADER);
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }

        String refreshToken = authorizationHeader.replace("Bearer ", "");
        Map<String, String> tokens = authServiceImpl.updateAccessToken(refreshToken);

        return ResponseEntity.ok(tokens);
    }

    @Override
    @DeleteMapping("/withdrawal")
    public ResponseEntity<String> withdraw(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody @Valid WithdrawalRequest request
    ) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new DomainException(ExceptionType.MISSING_AUTH_HEADER);
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }

        String refreshToken = authorizationHeader.replace("Bearer ", "");
        authServiceImpl.withdrawMember(refreshToken, request.password());

        return ResponseEntity.ok("{\"message\": \"회원 탈퇴가 완료되었습니다.\"}");
    }

    @DeleteMapping("/social/withdrawal")
    public ResponseEntity<Map<String, String>> withdrawSocial(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new DomainException(ExceptionType.MISSING_AUTH_HEADER);
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
        }

        String refreshToken = authorizationHeader.replace("Bearer ", "");
        authServiceImpl.withdrawSocialMember(refreshToken);

        return ResponseEntity.ok(Map.of("message", "회원 탈퇴가 성공적으로 처리되었습니다."));
    }
}
