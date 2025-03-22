package com.tutti.server.core.order.payload.response;

import java.util.List;
import lombok.Builder;

@Builder
public record OrderPageResponse(

        int totalProductAmount,
        int totalDiscountAmount,
        int deliveryFee,
        int totalAmount,

        List<OrderItemResponse> orderItems

) {

}
