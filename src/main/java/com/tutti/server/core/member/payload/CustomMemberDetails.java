//package com.tutti.server.core.member.payload;
//
//import com.tutti.server.core.member.domain.Member;
//import java.util.Collection;
//import java.util.List;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public record CustomMemberDetails(Member member) implements UserDetails {
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(() -> "ROLE_" + member.getMemberStatus().name()); // ✅ MemberStatus 기반
//    }
//
//    @Override
//    public String getPassword() {
//        return member.getPassword(); // ✅ 이메일 회원은 비밀번호가 존재, 소셜 로그인은 null 가능
//    }
//
//    @Override
//    public String getUsername() {
//        return member.getEmail(); // ✅ UserDetails을 implements
//    }
//
//    //추후 확장 가능성
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return member.isEmailVerified();
//    }
//}
