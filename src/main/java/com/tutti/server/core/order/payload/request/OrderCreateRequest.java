package com.tutti.server.core.order.payload.request;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.order.domain.CreatedByType;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderHistory;
import com.tutti.server.core.order.domain.OrderItem;
import com.tutti.server.core.order.domain.OrderStatus;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.product.domain.ProductItem;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderCreateRequest(

        Long memberId,
        List<OrderItemRequest> orderItems,
        PaymentMethodType paymentType
) {

    public record OrderItemRequest(

            Long productItemId,
            int quantity
    ) {

        public OrderItem toEntity(Order order, ProductItem productItem) {
            return OrderItem.builder()
                    .order(order)
                    .productItem(productItem)
                    .productName(productItem.getProduct().getName())
                    .productImgUrl(productItem.getProduct().getTitleUrl())
                    .productOptionName(productItem.getOptionName())
                    .productOptionValue(productItem.getOptionValue())
                    .quantity(quantity)
                    .price(productItem.getSellingPrice())
                    .build();
        }
    }

    public Order toEntity(Member member, String orderNumber, int orderCount, int totalAmount,
            String orderStatus) {
        return Order.builder()
                .member(member)
                .paymentType(paymentType)
                .orderStatus(orderStatus)
                .orderNumber(orderNumber)
                .orderCount(orderCount)
                .deliveryFee(0)
                .totalAmount(totalAmount)
                .build();
    }

    public OrderHistory toEntity(Order order, CreatedByType createdByType, long createdById,
            OrderStatus orderStatus) {
        return OrderHistory.builder()
                .order(order)
                .orderStatus(orderStatus)
                .createdByType(createdByType)
                .createdById(createdById)
                .latestVersion(true)
                .build();
    }
}
