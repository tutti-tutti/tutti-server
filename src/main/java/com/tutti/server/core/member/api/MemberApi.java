package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.MemberServiceImpl;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.payload.SignupRequest;
import com.tutti.server.core.member.payload.SocialLoginRequest;
import com.tutti.server.core.member.payload.SocialSignupRequest;
import com.tutti.server.core.member.payload.TermsAgreementRequest;
import com.tutti.server.core.member.security.JWTUtil;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi implements MemberApiSpec {

    private final MemberServiceImpl memberServiceImpl;
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    // 이메일 회원가입
    @Override
    public ResponseEntity<Map<String, String>> signup(SignupRequest request) {
        memberServiceImpl.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "회원가입이 완료되었습니다."));
    }

    // 소셜 회원가입
    @Override
    public ResponseEntity<Map<String, String>> socialSignup(SocialSignupRequest request) {
        var member = memberServiceImpl.socialSignup(request.email(), request.provider(),
                request.accessToken());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "소셜 회원가입이 완료되었습니다.", "member_id",
                        member.getId().toString()));
    }

    // 소셜 로그인
    @Override
    public ResponseEntity<Map<String, String>> socialLogin(SocialLoginRequest request) {
        Map<String, String> tokens = memberServiceImpl.socialLogin(request.provider(),
                request.accessToken());
        return ResponseEntity.ok(tokens);
    }

    // 약관 동의
    @Override
    public ResponseEntity<Map<String, String>> completeSocialSignup(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid List<TermsAgreementRequest> request) {

        // JWT에서 이메일 추출
        String email = jwtUtil.getEmail(token.replace("Bearer ", ""));

        // 이메일로 사용자 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));

        // `userId` 없이 회원가입 완료 처리
        memberServiceImpl.completeSocialSignup(member.getId(), request);

        return ResponseEntity.ok(Map.of("message", "회원가입이 최종 완료되었습니다."));
    }

}
