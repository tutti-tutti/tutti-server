package com.tutti.server.core.config;

import com.tutti.server.core.member.application.CustomOAuth2UserService;
import com.tutti.server.core.member.security.CustomSuccessHandler;
import com.tutti.server.core.member.security.JWTFilter;
import com.tutti.server.core.member.security.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(JWTUtil jwtUtil, UserDetailsService userDetailsService,
            CustomOAuth2UserService customOAuth2UserService,
            CustomSuccessHandler customSuccessHandler) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (API 요청을 위해 필요)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**")
//                        "members/email/verify",
//                                "members/email/confirm",
//                                "members/signup/email",
//                                "members/login/email")
                                .requestMatchers("/**") // ✅ 모든 URL 허용
                                .permitAll() // ✅ 로그인 관련 URL 허용
                                .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        // JWT 기반 인증 (세션 사용 X)
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)) // ✅ OAuth2 사용자 정보 처리
                        .successHandler(customSuccessHandler) // ✅ OAuth2 로그인 성공 시 JWT 발급
                )
                // ✅ JWTFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(new JWTFilter(jwtUtil, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.disable()) // 기본 로그인 폼 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()); // HTTP 기본 인증 비활성화

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
