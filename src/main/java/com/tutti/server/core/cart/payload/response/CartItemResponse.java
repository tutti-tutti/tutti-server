package com.tutti.server.core.cart.payload.response;

import com.tutti.server.core.cart.domain.CartItem;
import lombok.Builder;

@Builder
public record CartItemResponse(

        Long cartItemId,
        String storeName,
        Long productItemId,
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

    public static CartItemResponse fromEntity(CartItem cartItem) {
        // 이 빌더는 CartItemResponse 의 빌더
        return CartItemResponse.builder()
                .cartItemId(cartItem.getId())
                .storeName(cartItem.getProductItem().getProduct().getStoreId().getName())
                .productItemId(cartItem.getProductItem().getId())
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

