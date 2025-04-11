package com.tutti.server.core.order.payload.response;

import com.tutti.server.core.order.domain.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@Schema(description = "주문 상품 응답 DTO")
public record OrderItemResponse(

        @Schema(description = "판매자 스토어 ID", example = "1")
        Long storeId,

        @Schema(description = "판매자 스토어명", example = "Sony 공식몰")
        String storeName,

        @Schema(description = "부모 상품 ID", example = "92")
        Long productId,

        @Schema(description = "상품명", example = "LG전자 2024 LED FHD 스탠바이미 68cm (27ART10CMPL)")
        String productName,

        @Schema(description = "상품 썸네일 이미지 URL", example = "https://shopping-phinf.pstatic.net/main_4786597/47865974618.20240521000426.jpg")
        String productImgUrl,

        @Schema(description = "옵션별 상품 ID", example = "196")
        Long productItemId,

        @Schema(description = "첫번째 옵션명", example = "크기")
        String firstOptionName,

        @Schema(description = "첫번째 옵션값", example = "3.0")
        String firstOptionValue,

        @Schema(description = "두번째 옵션명", example = "구성품")
        String secondOptionName,

        @Schema(description = "두번째 옵션값", example = "512GB")
        String secondOptionValue,

        @Schema(description = "구매 수량", example = "1")
        int quantity,

        @Schema(description = "상품의 구매 가격", example = "281960")
        int price,

        @Schema(description = "예상 도착 일자", example = "2025-04-10T07:10:17.823Z")
        LocalDate expectedArrivalAt
) {

    public static OrderItemResponse fromEntity(OrderItem orderItem) {
        var store = orderItem.getStore();
        var product = orderItem.getProductItem().getProduct();

        return OrderItemResponse.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .productId(product.getId())
                .productName(orderItem.getProductName())
                .productImgUrl(orderItem.getProductImgUrl())
                .productItemId(orderItem.getProductItem().getId())
                .firstOptionName(orderItem.getFirstOptionName())
                .firstOptionValue(orderItem.getFirstOptionValue())
                .secondOptionName(orderItem.getSecondOptionName())
                .secondOptionValue(orderItem.getSecondOptionValue())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .expectedArrivalAt(orderItem.getExpectedArrivalAt())
                .build();
    }
}
