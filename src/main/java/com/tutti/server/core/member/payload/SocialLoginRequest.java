package com.tutti.server.core.member.payload;

public record SocialLoginRequest(
        String email,
        String provider,
        String socialId,
        String accessToken
) {

}