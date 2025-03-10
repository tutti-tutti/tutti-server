package com.tutti.server.core.payment.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull Long orderId, // 주문 Id
        @Min(1) int amount, // 결제 금액
        @NotNull String orderName // 주문명
) {
}
