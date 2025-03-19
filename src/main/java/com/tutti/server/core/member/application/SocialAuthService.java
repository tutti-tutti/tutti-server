package com.tutti.server.core.member.application;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SocialAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getSocialUserId(String provider, String accessToken) {
        String userInfoEndpoint = switch (provider.toLowerCase()) {
            case "google" -> "https://www.googleapis.com/oauth2/v3/userinfo";
            case "naver" -> "https://openapi.naver.com/v1/nid/me";
            default -> throw new IllegalArgumentException("지원하지 않는 소셜 로그인 제공자입니다.");
        };

        Map<String, Object> response = restTemplate.getForObject(
                userInfoEndpoint + "?access_token=" + accessToken, Map.class);
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 소셜 로그인 액세스 토큰입니다.");
        }

        return switch (provider.toLowerCase()) {
            case "google" -> (String) response.get("sub");
            case "naver" -> (String) ((Map<String, Object>) response.get("response")).get("id");
            default -> throw new IllegalArgumentException("지원하지 않는 소셜 로그인 제공자입니다.");
        };
    }
}
