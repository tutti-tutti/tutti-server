package com.tutti.server.core.payment.payload;

import jakarta.validation.constraints.NotNull;

public record PaymentCancelRequest(
        @NotNull Long orderId,
        @NotNull String cancelReason
) {

}
