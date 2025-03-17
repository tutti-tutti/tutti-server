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
        String orderId, // 요청을 orderId로 보내서 dto는 id로

        @Min(value = 1, message = "결제 금액은 최소 1원 이상이어야 합니다.")
        int amount,

        @NotBlank(message = "주문명은 필수입니다.")
        String orderName) {

    // 요청이 들어왔을때 첫 결제가 생성됨.
    public static Payment toEntity(Order order, Member member, int amount, String orderName,
            String orderNumber) {
        return Payment.builder()
                .orderName(orderName)
                .amount(amount)
                .paymentStatus(PaymentStatus.IN_PROGRESS)
                .tossPaymentKey(null)
                .member(member)
                .order(order)
                .paymentMethodType(order.getPaymentType())
                .orderNumber(orderNumber)
                .build();
    }
}
