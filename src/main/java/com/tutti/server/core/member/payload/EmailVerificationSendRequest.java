package com.tutti.server.core.member.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailVerificationSendRequest(
        @NotBlank @Email String email,
        @NotBlank String type
) {

}
