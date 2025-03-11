package com.tutti.server.core.member.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutti.server.core.member.payload.CustomMemberDetails;
import com.tutti.server.core.member.payload.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON 요청을 LoginRequest로 변환
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(),
                    LoginRequest.class);

            String username = loginRequest.email();  // JSON에서 email 가져오기
            String password = loginRequest.password();

//            System.out.println("로그인 시도: email = " + username);

            if (username == null || password == null) {
                throw new IllegalArgumentException("이메일 또는 비밀번호가 누락되었습니다.");
            }
//        //클라이언트 요청에서 username, password 추출
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
//        System.out.println(username);

            //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username, password, null);

            //token에 담은 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);
        } catch (
                IOException e) {
            throw new RuntimeException("로그인 요청을 처리할 수 없습니다.", e);
        }
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException {
        // ✅ CustomMemberDetails로 캐스팅 (Member 기반 인증)
        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();

        // ✅ username → email로 변경
        String email = customMemberDetails.getUsername();

        // ✅ 권한 가져오기 (ROLE_xxx 형식)
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        String role = iterator.hasNext() ? iterator.next().getAuthority() : "ROLE_USER"; // 기본값 설정

        // ✅ JWT 생성 (email 기반)
        String token = jwtUtil.createJwt(email, role, 60 * 60 * 10L); // 10시간
        // ✅ Refresh Token 생성 (유효 시간: 7일)

        String refreshToken = jwtUtil.createRefreshToken(email, 7 * 24 * 60 * 60 * 1000L);

        // ✅ 응답 헤더에 JWT 추가
        response.addHeader("Authorization", "Bearer " + token);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Map.of(
                "access_token", token,
                "refresh_token", refreshToken
        )));

    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(Map.of(
                "message", "로그인 실패",
                "error", failed.getMessage()
        )));
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("email");  // 기존 "username" 대신 "email" 사용
    }

}
