package com.tutti.server.core.order.payload.response;

import com.tutti.server.core.payment.domain.PaymentMethodType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderDetailResponse(

        String orderNumber,
        String orderStatus,

        // 결제 정보
        int totalProductAmount,
        int discountAmount,
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
        String note,

        // 판매 업체 정보
        String storeName
) {

}
