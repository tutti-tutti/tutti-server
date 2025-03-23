package com.tutti.server.core.payment.payload.request;

import jakarta.validation.constraints.NotNull;

public record PaymentCancelRequest(
        @NotNull String orderNumber,
        @NotNull String cancelReason
) {

}
