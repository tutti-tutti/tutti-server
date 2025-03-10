package com.tutti.server.core.member.dto;

import com.tutti.server.core.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomMemberDetails implements UserDetails {

    private final Member member;

    public CustomMemberDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "ROLE_" + member.getMemberStatus().name());  // ✅ MemberStatus 기반 권한 설정
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();  // ✅ 이메일 회원은 비밀번호가 존재, 소셜 로그인은 null 가능
    }

    @Override
    public String getUsername() {
        return member.getEmail();  // ✅ username 대신 email을 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return member.isEmailVerified();  // ✅ 이메일 인증된 사용자만 활성화
    }
}