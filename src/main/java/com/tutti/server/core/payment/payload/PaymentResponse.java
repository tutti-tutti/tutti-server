package com.tutti.server.core.payment.payload;

import com.tutti.server.core.payment.domain.Payment;
import lombok.Builder;

@Builder
public record PaymentResponse(

        String orderName,

        int amount,

        String orderNumber

) {

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .orderName(payment.getOrderName())
                .amount(payment.getAmount())
                .orderNumber(payment.getOrder().getOrderNumber())
                .build();
    }
}

