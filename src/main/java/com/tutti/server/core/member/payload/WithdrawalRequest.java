package com.tutti.server.core.member.payload;

import jakarta.validation.constraints.NotBlank;

public record WithdrawalRequest(
        @NotBlank
        String password
) {

}
