package com.tutti.server.core.cart.payload.response;

import com.tutti.server.core.cart.domain.CartItem;
import lombok.Builder;

@Builder
public record CartItemsResponse(

        Long cartItemId,
        String option,
        String productItemName,
        String productImgUrl,
        int quantity,
        int price,
        boolean soldOut
) {

    public static CartItemsResponse fromEntity(CartItem cartItem) {
        // 이 빌더는 CartItemsResponse 의 빌더
        return CartItemsResponse.builder()
                .cartItemId(cartItem.getId())
                .option(cartItem.getProductItemOption())
                .productItemName(cartItem.getProductName())
                .productImgUrl(cartItem.getProductImgUrl())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .soldOut(cartItem.isSoldOut())
                .build();
    }
}

