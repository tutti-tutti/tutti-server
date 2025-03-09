package com.tutti.server.core.cart.payload.response;

import com.tutti.server.core.cart.domain.CartItem;
import lombok.Builder;

@Builder
public record CartItemsResponse(

        Long cartItemId,
        String productItemName,
        String productImgUrl,
        int quantity,
        int price,
        boolean soldOut
) {

    public static CartItemsResponse fromEntity(CartItem cartItem) {
        return CartItemsResponse.builder()
                .cartItemId(cartItem.getId())
                .productItemName(cartItem.getProductName())
                .productImgUrl(cartItem.getProductImgUrl())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .soldOut(cartItem.isSoldOut())
                .build();
    }
}

