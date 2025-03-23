package com.tutti.server.core.order.payload.request;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.order.domain.CreatedByType;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderHistory;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderCreateRequest(

        @NotNull
        List<OrderItemRequest> orderItems,

        PaymentMethodType paymentType
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

    public OrderHistory toEntity(Order order, String orderStatus, CreatedByType createdByType,
            long createdById
    ) {
        return OrderHistory.builder()
                .order(order)
                .orderStatus(orderStatus)
                .createdByType(createdByType)
                .createdById(createdById)
                .latestVersion(true)
                .build();
    }
}
