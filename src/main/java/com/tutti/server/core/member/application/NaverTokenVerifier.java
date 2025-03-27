package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.SocialProvider;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("NAVER")
public class NaverTokenVerifier implements SocialTokenVerifier {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean verify(SocialProvider provider, String accessToken, String socialId) {
        if (provider != SocialProvider.NAVER) {
            return false;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://openapi.naver.com/v1/nid/me",
                    HttpMethod.GET,
                    request,
                    Map.class);

            Map<String, Object> userInfo = (Map<String, Object>) response.getBody().get("response");

            String idFromApi = (String) userInfo.get("id");
            return socialId.equals(idFromApi);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.SOCIAL_AUTH_SERVER_ERROR);
        }
    }
}
