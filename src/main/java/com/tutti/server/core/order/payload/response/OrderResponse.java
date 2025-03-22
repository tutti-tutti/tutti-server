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
        LocalDateTime completedAt,
        int totalAmount,
        String orderStatus,
        List<OrderItemResponse> orderItems
) {

    // 주문 정보를 추출하는 메서드
    public static OrderResponse fromEntity(Order order, List<OrderItem> orderItems) {
        List<OrderItemResponse> itemSummaries = orderItems.stream()
                .map(OrderItemResponse::fromEntity)
                .toList();

        return OrderResponse.builder()
                .orderNumber(order.getOrderNumber())
                .orderName(order.getOrderName())
                .completedAt(order.getCompletedAt())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .orderItems(itemSummaries)
                .build();
    }
}
