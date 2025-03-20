package com.tutti.server.core.member.application;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long memberId;
    private final String email;
    private final String password;

    public CustomUserDetails(long memberId, String email, String password) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
    }

    public long getMemberId() {
        return memberId;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
