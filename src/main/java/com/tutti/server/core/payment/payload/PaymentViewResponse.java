package com.tutti.server.core.payment.payload;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.payment.domain.Payment;
import java.time.LocalDateTime;

public record PaymentViewResponse(
        Long paymentId,
        String orderName,
        int amount,
        String paymentStatus,
        LocalDateTime completedAt,
        Long memberId,
        String memberName,
        Long orderId,
        String paymentMethodName
) {

    // Payment 엔티티로부터 PaymentViewResponse를 생성하는 메서드
    public static PaymentViewResponse fromEntity(Payment payment) {
        Order order = payment.getOrder();
        return new PaymentViewResponse(
                payment.getId(),
                payment.getOrderName(),
                payment.getAmount(),
                payment.getPaymentStatus().name(),
                payment.getCompletedAt(),
                payment.getMember().getId(),
                payment.getMember().getName(),
                order.getId(),
                payment.getPaymentMethod() != null ? payment.getPaymentMethod().getMethodType()
                        .name() : null
        );
    }

}
