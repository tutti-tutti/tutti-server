package com.tutti.server.core.member.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequest(
        @NotBlank @Email
        String email,
        @NotBlank String verificationCode,
        @NotBlank String newPassword
) {

}
