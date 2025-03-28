package com.tutti.server.core.order.payload.response;

import com.tutti.server.core.delivery.domain.Delivery;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderItem;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderDetailResponse(

        String orderNumber,
        String orderStatus,

        // 결제 정보
        int totalDiscountAmount,
        int totalProductAmount,
        int deliveryFee,
        int totalAmount,
        PaymentMethodType paymentType,

        // 날짜 정보
        LocalDateTime orderedAt,
        LocalDateTime paidAt,
        LocalDateTime deliveredAt,
        LocalDateTime completedAt,

        // 상품 정보
        List<OrderItemResponse> orderItems,

        // 배송 정보
        String recipientName,
        String recipientPhone,
        String recipientAddress,
        String zipCode,
        String note
) {

    public static OrderDetailResponse fromEntity(Order order, List<OrderItem> orderItems,
            Delivery delivery) {
        List<OrderItemResponse> itemSummaries = orderItems.stream()
                .map(OrderItemResponse::fromEntity)
                .toList();

        return OrderDetailResponse.builder()
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .totalDiscountAmount(order.getTotalDiscountAmount())
                .totalProductAmount(order.getTotalProductAmount())
                .deliveryFee(order.getDeliveryFee())
                .totalAmount(order.getTotalAmount())
                .paymentType(order.getPaymentType())
                .orderedAt(order.getCreatedAt())
                .paidAt(order.getPaidAt())
                .deliveredAt(order.getDeliveredAt())
                .completedAt(order.getCompletedAt())
                .orderItems(itemSummaries)
                .recipientName(delivery.getRecipientName())
                .recipientPhone(delivery.getRecipientPhone())
                .recipientAddress(delivery.getRecipientAddress())
                .zipCode(delivery.getZipcode())
                .note(delivery.getNote())
                .build();
    }
}
