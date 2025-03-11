package com.tutti.server.core.member.payload;

import com.tutti.server.core.member.domain.Member;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record CustomMemberDetails(Member member) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + member.getMemberStatus().name()); // ✅ MemberStatus 기반 권한 설정
    }

    @Override
    public String getPassword() {
        return member.getPassword(); // ✅ 이메일 회원은 비밀번호가 존재, 소셜 로그인은 null 가능
    }

    @Override
    public String getUsername() {
        return member.getEmail(); // ✅ username 대신 email을 사용
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
        return member.isEmailVerified(); // ✅ 이메일 인증된 사용자만 활성화
    }
}
