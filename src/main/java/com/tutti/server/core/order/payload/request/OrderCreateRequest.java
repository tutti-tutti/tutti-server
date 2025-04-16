package com.tutti.server.core.order.payload.request;

import com.tutti.server.core.delivery.domain.Delivery;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "주문 생성 요청 DTO")
public record OrderCreateRequest(

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

        // 상품 정보
        @NotNull(message = "주문할 상품을 선택해주세요.")
        @Schema(description = "주문할 상품 목록",
                example = """
                        [
                            {
                                "productItemId": 72,
                                "quantity": 1
                            },
                            {
                                "productItemId": 196,
                                "quantity": 1
                            }
                        ]
                        """
        )
        List<OrderItemRequest> orderItems,

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

    public Order toEntity(Member member, String orderStatus, String orderSheetNo, String orderName,
            int orderCount, int totalDiscountAmount, int totalProductAmount, int deliveryFee,
            int totalAmount
    ) {
        return Order.builder()
                .member(member)
                .paymentType(paymentType)
                .orderStatus(orderStatus)
                .orderSheetNo(orderSheetNo)
                .orderName(orderName)
                .orderCount(orderCount)
                .totalDiscountAmount(totalDiscountAmount)
                .totalProductAmount(totalProductAmount)
                .deliveryFee(deliveryFee)
                .totalAmount(totalAmount)
                .build();
    }

    public Delivery toEntity(Order order) {
        return Delivery.builder()
                .order(order)
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .recipientAddress(recipientAddress)
                .zipcode(zipCode)
                .note(note)
                .build();
    }
}
