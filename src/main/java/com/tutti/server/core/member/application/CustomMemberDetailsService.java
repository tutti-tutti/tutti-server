//package com.tutti.server.core.member.application;
//
//import com.tutti.server.core.member.infrastructure.MemberRepository;
//import com.tutti.server.core.member.payload.CustomMemberDetails;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomMemberDetailsService implements UserDetailsService {
//
//    private final MemberRepository memberRepository;
//
//    public CustomMemberDetailsService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return memberRepository.findByEmail(email)
//                .map(CustomMemberDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));
//    }
//}
//
