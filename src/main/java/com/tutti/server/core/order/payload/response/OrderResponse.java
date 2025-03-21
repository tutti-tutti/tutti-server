package com.tutti.server.core.order.payload.response;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderItem;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderResponse(
        String orderNumber,
        String orderName,
        LocalDateTime orderDate,
        int totalAmount,
        String orderStatus,
        List<OrderItemSummary> orderItems
) {

    // 주문 정보를 추출하는 메서드
    public static OrderResponse fromEntity(Order order, List<OrderItem> orderItems) {
        List<OrderItemSummary> itemSummaries = orderItems.stream()
                .map(OrderItemSummary::fromEntity)
                .toList();

        return OrderResponse.builder()
                .orderNumber(order.getOrderNumber())
                .orderName(order.getOrderName())
                .orderDate(order.getCompletedAt())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .orderItems(itemSummaries)
                .build();
    }

    @Builder
    public record OrderItemSummary(
            String productName,
            String productImgUrl,
            String firstOptionName,
            String firstOptionValue,
            String secondOptionName,
            String secondOptionValue,
            int quantity,
            int price
    ) {

        // 상품 정보를 추출하는 메서드
        public static OrderItemSummary fromEntity(OrderItem orderItem) {
            return OrderItemSummary.builder()
                    .productName(orderItem.getProductName())
                    .productImgUrl(orderItem.getProductImgUrl())
                    .firstOptionName(orderItem.getFirstOptionName())
                    .firstOptionValue(orderItem.getFirstOptionValue())
                    .secondOptionName(orderItem.getSecondOptionName())
                    .secondOptionValue(orderItem.getSecondOptionValue())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPrice())
                    .build();
        }
    }
}
