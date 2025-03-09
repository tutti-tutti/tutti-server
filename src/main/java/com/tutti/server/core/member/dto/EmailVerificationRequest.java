package com.tutti.server.core.member.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class EmailVerificationRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String verificationCode;

}
