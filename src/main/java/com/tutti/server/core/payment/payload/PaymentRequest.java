package com.tutti.server.core.payment.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(

        @NotNull(message = "주문 ID는 필수입니다.")
        Long orderId,

        @Min(value = 1, message = "결제 금액은 최소 1원 이상이어야 합니다.")
        int amount,

        @NotBlank(message = "주문명은 필수입니다.")
        String orderName) {

}
