package com.tutti.server.core.order.payload.response;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderItem;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderListResponse(List<OrderSummary> orderSummaries) {

    public static OrderListResponse fromEntity(List<Order> orders) {
        // order 엔티티 List 를 받아서 DTO List 로 만들기
        List<OrderSummary> orderSummaries = orders.stream()
                .map(OrderSummary::fromEntity)
                .toList();

        return OrderListResponse.builder()
                .orderSummaries(orderSummaries)
                .build();
    }

    @Builder
    public record OrderSummary(

            String orderNumber,
            LocalDateTime orderDate,
            int totalAmount,
            String orderStatus,
            List<OrderItemSummary> orderItems
    ) {

        public static List<OrderItemSummary> fromEntity(List<OrderItem> orderItems) {
            return orderItems.stream()
                    .map(OrderItemSummary::fromEntity)
                    .toList();

        public static OrderSummary fromEntity(Order order) {
            return OrderSummary.builder()
                    .orderNumber(order.getOrderNumber())
                    .orderDate(order.getCompletedAt())
                    .totalAmount(order.getTotalAmount())
                    .orderStatus(order.getOrderStatus())
                    .orderItems(itemSummaries)
                    .build();
        }
    }

    @Builder
    public record OrderItemSummary(

            String productName,
            String productImgUrl,
            String productOptionName,
            String productOptionValue,
            int quantity,
            int price
    ) {

        public static OrderItemSummary fromEntity(OrderItem orderItem) {
            return OrderItemSummary.builder()
                    .productName(orderItem.getProductName())
                    .productImgUrl(orderItem.getProductImgUrl())
                    .productOptionName(orderItem.getProductOptionName())
                    .productOptionValue(orderItem.getProductOptionValue())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPrice())
                    .build();
        }
    }
}

