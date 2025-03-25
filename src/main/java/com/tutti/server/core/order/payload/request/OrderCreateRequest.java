package com.tutti.server.core.order.payload.request;

import com.tutti.server.core.delivery.domain.Delivery;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderCreateRequest(

        int totalDiscountAmount,
        int totalProductAmount,
        int deliveryFee,
        int totalAmount,
        PaymentMethodType paymentType,

        @NotNull(message = "주문할 상품을 선택해주세요.")
        List<OrderItemRequest> orderItems,

        // 배송 정보
        String recipientName,
        String recipientPhone,
        String recipientAddress,
        String zipCode,
        String note
) {

    public Order toEntity(Member member, String orderStatus, String orderNumber, String orderName,
            int orderCount, int totalDiscountAmount, int totalProductAmount, int deliveryFee,
            int totalAmount
    ) {
        return Order.builder()
                .member(member)
                .paymentType(paymentType)
                .orderStatus(orderStatus)
                .orderNumber(orderNumber)
                .orderName(orderName)
                .orderCount(orderCount)
                .totalDiscountAmount(totalDiscountAmount)
                .totalProductAmount(totalProductAmount)
                .deliveryFee(deliveryFee)
                .totalAmount(totalAmount)
                .build();
    }

    public Delivery toEntity(Order order) {
        return Delivery.builder()
                .order(order)
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .recipientAddress(recipientAddress)
                .zipcode(zipCode)
                .note(note)
                .build();
    }
}
