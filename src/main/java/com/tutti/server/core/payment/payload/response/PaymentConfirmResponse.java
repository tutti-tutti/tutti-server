package com.tutti.server.core.payment.payload.response;

import lombok.Builder;

@Builder
public record PaymentConfirmResponse(
        Long orderId
) {

    public static PaymentConfirmResponse fromEntity(Long orderId) {
        return PaymentConfirmResponse.builder()
                .orderId(orderId)
                .build();
    }
}
