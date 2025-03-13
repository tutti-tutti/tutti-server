package com.tutti.server.core.payment.payload;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
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

    // 요청이 들어왔을때 첫 결제가 생성됨.
    public static Payment toEntity(Order order, Member member, int amount, String orderName) {
        return Payment.builder()
                .orderName(orderName)
                .amount(amount)
                .paymentStatus(PaymentStatus.PAYMENT_REQUESTED)
                .tossPaymentKey(null)
                .member(member)
                .order(order)
                .paymentMethod(null)
                .build();
    }

}
