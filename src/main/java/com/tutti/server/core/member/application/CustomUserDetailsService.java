package com.tutti.server.core.member.application;

import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(member -> new org.springframework.security.core.userdetails.User(
                        member.getEmail(),
                        member.getPassword(),
                        List.of() // 권한 없음
                ))
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));
    }
}
