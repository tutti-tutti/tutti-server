package com.tutti.server.core.cart.payload.response;

import com.tutti.server.core.cart.domain.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CartItemResponse(

        @Schema(description = "장바구니 상품 ID", example = "3")
        Long cartItemId,

        @Schema(description = "판매자 스토어 ID", example = "2")
        Long storeId,

        @Schema(description = "판매자 스토어명", example = "LG 공식스토어")
        String storeName,

        @Schema(description = "부모 상품 ID", example = "75")
        Long productId,

        @Schema(description = "상품명", example = "LG전자 MW23GD")
        String productItemName,

        @Schema(description = "상품 썸네일 이미지 URL", example = "https://shopping-phinf.pstatic.net/main_1060273/10602735096.20170320175334.jpg")
        String productImgUrl,

        @Schema(description = "옵션별 상품 ID", example = "155")
        Long productItemId,

        @Schema(description = "첫번째 옵션명", example = "색상")
        String firstOptionName,

        @Schema(description = "첫번째 옵션값", example = "블랙")
        String firstOptionValue,

        @Schema(description = "두번째 옵션명", example = "기능 추가")
        String secondOptionName,

        @Schema(description = "두번째 옵션값", example = "단일 제품")
        String secondOptionValue,

        @Schema(description = "원가(product's price)", example = "1000000")
        int originalPrice,

        @Schema(description = "판매가(원가 + 옵션 추가 금액 - 할인 금액)", example = "1000850")
        int sellingPrice,

        @Schema(description = "수량", example = "1")
        int quantity,

        @Schema(description = "최대 수량", example = "10", pattern = "product's maxQuantity")
        int maxQuantity,

        @Schema(description = "품절 여부(true/false)", example = "false")
        boolean soldOut,

        @Schema(description = "장바구니 상품 체크 여부(true/false)", example = "true")
        boolean checked
) {

    public static CartItemResponse fromEntity(CartItem cartItem) {
        var productItem = cartItem.getProductItem();
        var product = productItem.getProduct();
        var store = product.getStoreId();

        // 이 빌더는 CartItemResponse 의 빌더
        return CartItemResponse.builder()
                .cartItemId(cartItem.getId())
                .storeId(store.getId())
                .storeName(store.getName())
                .productId(product.getId())
                .productItemName(cartItem.getProductName())
                .productImgUrl(cartItem.getProductImgUrl())
                .productItemId(productItem.getId())
                .firstOptionName(cartItem.getFirstOptionName())
                .firstOptionValue(cartItem.getFirstOptionValue())
                .secondOptionName(cartItem.getSecondOptionName())
                .secondOptionValue(cartItem.getSecondOptionValue())
                .originalPrice(cartItem.getOriginalPrice())
                .sellingPrice(cartItem.getSellingPrice())
                .quantity(cartItem.getQuantity())
                .maxQuantity(product.getMaxQuantity())
                .soldOut(cartItem.isSoldOut())
                .checked(true)
                .build();
    }
}

