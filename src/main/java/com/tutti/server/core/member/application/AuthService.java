package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.MemberStatus;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.jwt.JWTUtil;
import com.tutti.server.core.member.payload.LoginRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public Map<String, String> login(LoginRequest request) {
        // 1. 이메일 존재 여부 확인
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

        // 2. 이메일 인증 여부 확인
        if (!member.isEmailVerified()) {
            throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
        }

        // 3. 계정 상태 확인
        if (member.getMemberStatus() == MemberStatus.WITHDRAWN) {
            throw new IllegalArgumentException("탈퇴한 계정입니다.");
        }

        if (member.getMemberStatus() == MemberStatus.BANNED) {
            throw new IllegalArgumentException("정지된 계정입니다.");
        }

        // 4. 비밀번호 검증
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 5. JWT 토큰 생성
        String accessToken = jwtUtil.createJwt(member.getEmail(), member.getMemberStatus().name(),
                JWTUtil.ACCESS_TOKEN_EXPIRATION);
        String refreshToken = jwtUtil.createRefreshToken(member.getEmail(),
                JWTUtil.REFRESH_TOKEN_EXPIRATION);

        return Map.of(
                "access_token", accessToken,
                "refresh_token", refreshToken
        );
    }
}

