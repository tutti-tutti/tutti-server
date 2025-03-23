package com.tutti.server.core.order.payload.response;

import java.util.List;
import lombok.Builder;

@Builder
public record OrderPageResponse(

        int totalDiscountAmount,
        int totalProductAmount,
        int deliveryFee,
        int totalAmount,

        List<OrderItemResponse> orderItems
) {

}
