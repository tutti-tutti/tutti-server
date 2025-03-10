package com.tutti.server.core.member.jwt;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.dto.CustomMemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // ✅ 요청에서 Authorization 헤더 가져오기
        String authorization = request.getHeader("Authorization");

        // ✅ Authorization 헤더가 없거나 "Bearer "로 시작하지 않는 경우 요청 통과
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ✅ Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.replace("Bearer ", "");

        // ✅ 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
            return;
        }

        // ✅ 토큰에서 email과 role 획득
        String email = jwtUtil.getUsername(token);
//        String role = jwtUtil.getRole(token);

        // ✅ SecurityContext에 저장
        Authentication authToken = new UsernamePasswordAuthenticationToken(email, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

        // ✅ Member 객체를 생성하여 값 설정 (임시 객체)
//        Member member = new Member();
//        member.setEmail(email);
//        member.setMemberStatus(com.tutti.server.core.member.domain.MemberStatus.ACTIVE);  // ✅ 기본값 설정

        // ✅ UserDetails에 회원 정보 객체 담기
//        CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

        // ✅ Spring Security 인증 토큰 생성
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());
//
//        // ✅ 세션에 사용자 등록
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        // ✅ 다음 필터 실행
//        filterChain.doFilter(request, response);
    }
}
