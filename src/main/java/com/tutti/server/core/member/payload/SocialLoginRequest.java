package com.tutti.server.core.member.payload;

import jakarta.validation.constraints.NotBlank;

public record SocialLoginRequest(
        @NotBlank String provider,
        @NotBlank String accessToken) {

}