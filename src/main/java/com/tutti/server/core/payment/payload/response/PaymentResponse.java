package com.tutti.server.core.payment.payload.response;

import com.tutti.server.core.payment.domain.Payment;
import lombok.Builder;

@Builder
public record PaymentResponse(

        String orderName,

        int amount,

        String orderSheetNo

) {

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .orderName(payment.getOrderName())
                .amount(payment.getAmount())
                .orderSheetNo(payment.getOrder().getOrderSheetNo())
                .build();
    }
}

