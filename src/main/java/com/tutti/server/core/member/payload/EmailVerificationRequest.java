package com.tutti.server.core.member.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailVerificationRequest(
        @Email @NotBlank String email,
        @NotBlank String verificationCode
) {

}

