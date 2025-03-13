package com.tutti.server.core.payment.payload;

import jakarta.validation.constraints.NotBlank;

public record PaymentConfirmRequest(
        @NotBlank String paymentKey,
        @NotBlank String orderId,
        @NotBlank int amount
) {

}
