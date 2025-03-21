package com.tutti.server.core.member.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.member.application.CustomUserDetailsService;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionResponse;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환 객체 추가

    public JWTFilter(JWTUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorization.replace("Bearer ", "").trim();

            if (jwtUtil.isExpired(token)) {
                throw new DomainException(ExceptionType.TOKEN_EXPIRED);
            }

            Long memberId = jwtUtil.getMemberId(token);
            if (memberId == null) {
                throw new DomainException(ExceptionType.INVALID_JWT_TOKEN);
            }

            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserById(
                    memberId);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (DomainException ex) {
            handleException(response, ex);
        }
    }

    private void handleException(HttpServletResponse response, DomainException ex)
            throws IOException {
        response.setStatus(ex.getExceptionType().getStatus().value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(
                ExceptionResponse.from(ex.getExceptionType())));
    }
}
