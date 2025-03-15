package com.tutti.server.core.payment.payload;

import lombok.Builder;

@Builder
public record PaymentCancelRequest(
        Long orderId,
        String cancelReason
) {

    public String getCancelReason() {
        return cancelReason();
    }

}
