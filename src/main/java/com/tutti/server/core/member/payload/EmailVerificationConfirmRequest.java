package com.tutti.server.core.member.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailVerificationConfirmRequest(
        @Email @NotBlank String email,
        @NotBlank String verificationCode
) {

}
