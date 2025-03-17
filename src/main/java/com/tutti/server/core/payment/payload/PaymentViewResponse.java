package com.tutti.server.core.payment.payload;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.time.LocalDateTime;
import java.util.Optional;

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
        return new PaymentViewResponse(
                payment.getId(),
                payment.getOrderName(),
                payment.getAmount(),
                payment.getPaymentStatus().name(),
                payment.getCompletedAt(),
                payment.getMember().getId(),
                payment.getMember().getName(),
                payment.getOrder().getId(),
                Optional.ofNullable(payment.getPaymentMethod())
                        .map(method -> method.getMethodType().name())
                        .orElseThrow(() -> new DomainException(
                                ExceptionType.MISSING_METHOD_NOT_FOUND))
        );
    }
}
