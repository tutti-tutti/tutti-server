package com.tutti.server.core.member.jwt;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String email;

    public JwtAuthenticationToken(String email) {
        super(null);
        this.email = email;
        setAuthenticated(false); // 기본적으로 인증되지 않은 상태
    }

    public JwtAuthenticationToken(String email,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        setAuthenticated(true); // 인증된 상태
    }

    @Override
    public Object getCredentials() {
        return null; // JWT 기반 인증이므로 비밀번호 없음
    }

    @Override
    public Object getPrincipal() {
        return email; // 사용자의 email을 principal로 사용
    }
}

