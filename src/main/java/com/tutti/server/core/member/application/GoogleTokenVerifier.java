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

@Component("GOOGLE")
public class GoogleTokenVerifier implements SocialTokenVerifier {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean verify(SocialProvider provider, String accessToken, String socialId) {
        if (provider != SocialProvider.GOOGLE) {
            return false;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://www.googleapis.com/oauth2/v3/userinfo",
                    HttpMethod.GET,
                    request,
                    Map.class
            );

            Map<String, Object> userInfo = response.getBody();
            String idFromApi = (String) userInfo.get("sub");

            return socialId.equals(idFromApi);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.SOCIAL_AUTH_SERVER_ERROR);
        }
    }
}
