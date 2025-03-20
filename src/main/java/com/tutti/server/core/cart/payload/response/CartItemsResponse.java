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
        String productOptionName1,
        String productOptionValue1,
        String productOptionName2,
        String productOptionValue2,
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
                .productItemName(cartItem.getProductName())
                .productImgUrl(cartItem.getProductImgUrl())
                .productOptionName1(cartItem.getProductOptionName_1())
                .productOptionValue1(cartItem.getProductOptionValue_1())
                .productOptionName2(cartItem.getProductOptionName_2())
                .productOptionValue2(cartItem.getProductOptionValue_2())
                .originalPrice(cartItem.getOriginalPrice())
                .sellingPrice(cartItem.getSellingPrice())
                .quantity(cartItem.getQuantity())
                .maxQuantity(10)
                .soldOut(cartItem.isSoldOut())
                .build();
    }
}

