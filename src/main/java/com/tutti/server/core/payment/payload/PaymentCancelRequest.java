package com.tutti.server.core.payment.payload;

public record PaymentCancelRequest(
        Long orderId,
        String cancelReason
) {

}
