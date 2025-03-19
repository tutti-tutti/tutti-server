package com.tutti.server.core.member.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    public CustomSuccessHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customUser = (CustomOAuth2User) authentication.getPrincipal();
        String token = jwtUtil.createJwt(customUser.email(), "ROLE_USER",
                JWTUtil.ACCESS_TOKEN_EXPIRATION);
        response.addHeader("Authorization", "Bearer " + token);
        response.sendRedirect("http://localhost:3000/");
    }
}
