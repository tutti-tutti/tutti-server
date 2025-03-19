package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.SocialProvider;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.payload.GoogleResponse;
import com.tutti.server.core.member.payload.NaverResponse;
import com.tutti.server.core.member.payload.OAuth2Response;
import com.tutti.server.core.member.security.CustomOAuth2User;
import java.util.Optional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인 제공자입니다.");
        }

        String socialId = oAuth2Response.getProviderId();
        String email = oAuth2Response.getEmail();

        // ✅ 기존 회원 찾기
        Optional<Member> existingMember = memberRepository.findBySocialId(socialId);

        if (existingMember.isEmpty()) {
            // ✅ 신규 회원 저장
            Member newMember = Member.createSocialMember(socialId,
                    SocialProvider.valueOf(registrationId.toUpperCase()), email);
            memberRepository.save(newMember);
            System.out.println("✅ 소셜 로그인 신규 회원 저장: " + newMember.getEmail());
        } else {
            System.out.println("✅ 기존 소셜 로그인 회원 존재: " + existingMember.get().getEmail());
        }

        return new CustomOAuth2User(email, "ROLE_USER", oAuth2User.getAttributes());
    }
}

