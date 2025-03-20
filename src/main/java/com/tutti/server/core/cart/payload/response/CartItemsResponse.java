package com.tutti.server.core.cart.payload.response;

import com.tutti.server.core.cart.domain.CartItem;
import lombok.Builder;

@Builder
public record CartItemsResponse(

        Long cartItemId,
        String storeName,
        Long productId,
        String productItemName,
        String productImgUrl,
        String firstOptionName,
        String firstOptionValue,
        String secondOptionName,
        String secondOptionValue,
        int originalPrice,
        int sellingPrice,
        int quantity,
        int maxQuantity,
        boolean soldOut
) {

    public static CartItemsResponse fromEntity(CartItem cartItem) {
        // 이 빌더는 CartItemsResponse 의 빌더
        return CartItemsResponse.builder()
                .cartItemId(cartItem.getId())
                .storeName(cartItem.getProductItem().getProduct().getStoreId().getName())
                .productId(cartItem.getProductItem().getProduct().getId())
                .productItemName(cartItem.getProductName())
                .productImgUrl(cartItem.getProductImgUrl())
                .firstOptionName(cartItem.getFirstOptionName())
                .firstOptionValue(cartItem.getFirstOptionValue())
                .secondOptionName(cartItem.getSecondOptionName())
                .secondOptionValue(cartItem.getSecondOptionValue())
                .originalPrice(cartItem.getOriginalPrice())
                .sellingPrice(cartItem.getSellingPrice())
                .quantity(cartItem.getQuantity())
                .maxQuantity(10)
                .soldOut(cartItem.isSoldOut())
                .build();
    }
}

