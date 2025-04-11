package com.tutti.server.core.order.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "주문서 작성 페이지 응답 DTO")
public record OrderPageResponse(

        @Schema(description = "총 할인 금액", example = "1000")
        int totalDiscountAmount,

        @Schema(description = "총 상품 금액(원가 + 옵션 추가 금액)", example = "1682950")
        int totalProductAmount,

        @Schema(description = "배송비", example = "3000")
        int deliveryFee,

        @Schema(description = "총 결제 금액(총 상품 금액 - 총 할인 금액 + 배송비)", example = "1684950")
        int totalAmount,

        @Schema(description = "주문 상품 목록",
                example = """
                        [
                            {
                                "storeId": 4,
                                "storeName": "홈 & 테크 쇼핑몰",
                                "productId": 32,
                                "productName": "아이폰 16 128GB [자급제]",
                                "productImgUrl": "https://shopping-phinf.pstatic.net/main_5023842/50238421618.20240910101919.jpg",
                                "productItemId": 72,
                                "firstOptionName": "버전",
                                "firstOptionValue": "아이폰 16 Pro",
                                "secondOptionName": "패키지",
                                "secondOptionValue": "단일 제품",
                                "quantity": 1,
                                "price": "1400820",
                                "expectedArrivalAt": "2025-04-10T07:10:17.823Z"
                            },
                            {
                                "storeId": 1,
                                "storeName": "Sony 공식몰",
                                "productId": 92,
                                "productName": "LG전자 2024 LED FHD 스탠바이미 68cm (27ART10CMPL)",
                                "productImgUrl": "https://shopping-phinf.pstatic.net/main_4786597/47865974618.20240521000426.jpg",
                                "productItemId": 196,
                                "firstOptionName": "크기",
                                "firstOptionValue": "3.0",
                                "secondOptionName": "구성품",
                                "secondOptionValue": "512GB",
                                "quantity": 1,
                                "price": "281960",
                                "expectedArrivalAt": "2025-04-10T07:10:17.823Z"
                            }
                        ]
                        """
        )
        List<OrderItemResponse> orderItems
) {

}
