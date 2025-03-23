package com.tutti.server.core.payment.payload.response;

import com.tutti.server.core.payment.domain.Payment;
import java.time.LocalDateTime;

public record PaymentViewResponse(
        Long paymentId,
        String orderName,
        int amount,
        String paymentStatus,
        LocalDateTime completedAt,
        Long orderId,
        String paymentMethodName
) {

    // Payment 엔티티로부터 PaymentViewResponse를 생성하는 메서드
    public static PaymentViewResponse fromEntity(Payment payment) {
        return new PaymentViewResponse(
                payment.getId(),
                payment.getOrderName(),
                payment.getAmount(),
                payment.getPaymentStatus(),
                payment.getCompletedAt(),
                payment.getOrder().getId(),
                payment.getPaymentMethodType().name()
        );
    }
}
