package com.tutti.server.core.payment.payload;

import com.tutti.server.core.payment.domain.Payment;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PaymentResponse(
        Long paymentId,

        String paymentStatus,

        String orderName,

        int amount,

        Long orderId,

        LocalDateTime createdAt
) {

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .paymentStatus(payment.getPaymentStatus())
                .orderName(payment.getOrderName())
                .amount(payment.getAmount())
                .orderId(payment.getOrder().getId())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}

