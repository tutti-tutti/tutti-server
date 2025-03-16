package com.tutti.server.core.payment.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PaymentConfirmRequest(

        @NotBlank(message = "paymentKey는 필수입니다.")
        String paymentKey,

        @NotBlank(message = "주문 ID는 필수입니다.")
        String orderId, // 수정 불가

        @Min(1)
        int amount
) {

}
