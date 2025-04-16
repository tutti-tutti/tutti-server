package com.tutti.server.core.order.payload.response;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "주문 내역 전체 조회 응답 DTO")
public record OrderResponse(

        @Schema(description = "주문 ID(시스템용)", example = "105189", pattern = "null ~ 2^63 - 1")
        Long orderId,

        @Schema(description = "주문 번호(고객 확인용)", example = "20250122-3f2b1a5d", pattern = "{Today's Date}-{UUID.Random()}")
        String orderSheetNo,

        @Schema(description = "주문 상태", example = "READY")
        String orderStatus,

        @Schema(description = "주문명", example = "아이폰 16 128GB [자급제] 외 1건", pattern = "{First OrderItem's Name} + 외 + {OrderCount - 1} + 건")
        String orderName,

        @Schema(description = "주문 생성 일자(주문서 작성 후, 결제 요청을 보낸 시점)", example = "2025-04-10T07:10:17.823Z", pattern = "yyyy-MM-ddTHH:mm:ss")
        LocalDateTime createdAt,

        @Schema(description = "총 결제 금액(총 상품 금액 - 할인 금액 - 배송비)", example = "21900")
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

    // 주문 정보를 추출하는 메서드
    public static OrderResponse fromEntity(Order order, List<OrderItem> orderItems) {
        List<OrderItemResponse> itemSummaries = orderItems.stream()
                .map(OrderItemResponse::fromEntity)
                .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderSheetNo(order.getOrderSheetNo())
                .orderStatus(order.getOrderStatus())
                .orderName(order.getOrderName())
                .createdAt(order.getCreatedAt())
                .totalAmount(order.getTotalAmount())
                .orderItems(itemSummaries)
                .build();
    }
}
