package com.tutti.server.core.order.payload.response;

import com.tutti.server.core.order.domain.OrderItem;
import lombok.Builder;

@Builder
public record OrderItemResponse(

        Long productItemId,
        String productName,
        String productImgUrl,
        String firstOptionName,
        String firstOptionValue,
        String secondOptionName,
        String secondOptionValue,
        int quantity,
        int price
) {

    public static OrderItemResponse fromEntity(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .productItemId(orderItem.getProductItem().getId())
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
