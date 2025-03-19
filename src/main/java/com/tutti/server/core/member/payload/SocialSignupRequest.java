package com.tutti.server.core.member.payload;

import jakarta.validation.constraints.NotBlank;

public record SocialSignupRequest(
        @NotBlank String email,
        @NotBlank String provider,
        @NotBlank String accessToken) {

}