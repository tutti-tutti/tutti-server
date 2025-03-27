package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.SocialProvider;

public interface SocialTokenVerifier {

    boolean verify(SocialProvider provider, String accessToken, String socialId);
}
