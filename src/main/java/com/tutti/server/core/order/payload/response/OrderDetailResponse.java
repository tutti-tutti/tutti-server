package com.tutti.server.core.order.payload.response;

import com.tutti.server.core.delivery.domain.Delivery;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderItem;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "주문 내역 상세 조회 응답 DTO")
public record OrderDetailResponse(

        // 주문 정보
        @Schema(description = "주문 ID(시스템용)", example = "105189", pattern = "null ~ 2^63 - 1")
        Long orderId,

        @Schema(description = "주문 번호(고객 확인용)", example = "20250122-3f2b1a5d", pattern = "{Today's Date}-{UUID.Random}")
        String orderSheetNo,

        @Schema(description = "주문 상태", example = "READY")
        String orderStatus,

        // 결제 정보
        @Schema(description = "총 할인 금액", example = "1000")
        int totalDiscountAmount,

        @Schema(description = "총 상품 금액(원가 + 옵션 추가 금액)", example = "1682950")
        int totalProductAmount,

        @Schema(description = "배송비", example = "3000")
        int deliveryFee,

        @Schema(description = "총 결제 금액(총 상품 금액 - 총 할인 금액 + 배송비)", example = "1684950")
        int totalAmount,

        @Schema(description = "결제 수단", example = "CARD")
        PaymentMethodType paymentType,

        // 날짜 정보
        @Schema(description = "주문 생성 일자(주문서 작성 후, 결제 요청을 보낸 시점)", example = "2025-04-10T07:10:17.823Z", pattern = "yyyy-MM-ddTHH:mm:ss")
        LocalDateTime createdAt,

        @Schema(description = "결제 완료 일자", example = "2025-04-10T07:12:11.823Z", pattern = "yyyy-MM-ddTHH:mm:ss")
        LocalDateTime paidAt,

        @Schema(description = "배송 완료 일자", example = "2025-04-13T15:00:10.823Z", pattern = "yyyy-MM-ddTHH:mm:ss")
        LocalDateTime deliveredAt,

        @Schema(description = "주문 완료 일자(배송 완료 후 24시간 이내)", example = "2025-04-13T23:00:10.823Z", pattern = "yyyy-MM-ddTHH:mm:ss")
        LocalDateTime completedAt,

        // 상품 정보
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
        List<OrderItemResponse> orderItems,

        // 배송 정보
        @Schema(description = "받는 사람 이름", example = "이지혜")
        String recipientName,

        @Schema(description = "받는 사람 연락처", example = "01012345678")
        String recipientPhone,

        @Schema(description = "우편 번호", example = "06035")
        String zipCode,

        @Schema(description = "받는 주소", example = "서울 강남구 가로수길 9")
        String recipientAddress,

        @Schema(description = "상세 주소", example = "10층 1004호")
        String detailAddress,

        @Schema(description = "배송 요청 사항", example = "빠른 배송 부탁드려요~!")
        String note
) {

    public static OrderDetailResponse fromEntity(Order order, List<OrderItem> orderItems,
            Delivery delivery) {
        List<OrderItemResponse> itemSummaries = orderItems.stream()
                .map(OrderItemResponse::fromEntity)
                .toList();

        return OrderDetailResponse.builder()
                .orderId(order.getId())
                .orderSheetNo(order.getOrderSheetNo())
                .orderStatus(order.getOrderStatus())
                .totalDiscountAmount(order.getTotalDiscountAmount())
                .totalProductAmount(order.getTotalProductAmount())
                .deliveryFee(order.getDeliveryFee())
                .totalAmount(order.getTotalAmount())
                .paymentType(order.getPaymentType())
                .createdAt(order.getCreatedAt())
                .paidAt(order.getPaidAt())
                .deliveredAt(order.getDeliveredAt())
                .completedAt(order.getCompletedAt())
                .orderItems(itemSummaries)
                .recipientName(delivery.getRecipientName())
                .recipientPhone(delivery.getRecipientPhone())
                .recipientAddress(delivery.getRecipientAddress())
                .zipCode(delivery.getZipcode())
                .note(delivery.getNote())
                .build();
    }
}
