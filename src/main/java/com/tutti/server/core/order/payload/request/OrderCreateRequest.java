package com.tutti.server.core.order.payload.request;

import com.tutti.server.core.cart.domain.CartItem;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.order.domain.CreatedByType;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderHistory;
import com.tutti.server.core.order.domain.OrderItem;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.product.domain.ProductItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderCreateRequest(

        Long memberId,
        List<OrderItemRequest> orderItems,
        PaymentMethodType paymentType
) {

    public record OrderItemRequest(

            @NotNull(message = "필수 옵션을 선택해주세요.")
            Long productItemId,

            @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
            int quantity
    ) {

        public OrderItem toEntity(Order order, ProductItem productItem) {
            return OrderItem.builder()
                    .order(order)
                    .productItem(productItem)
                    .productName(productItem.getProduct().getName())
                    .productImgUrl(productItem.getProduct().getTitleUrl())
                    .productOptionName(productItem.getOptionName())
                    .productOptionValue(productItem.getOptionValue())
                    .quantity(quantity)
                    .price(productItem.getSellingPrice())
                    .build();
        }

        public OrderItem toEntity(Order order, CartItem cartItem) {
            return OrderItem.builder()
                    .order(order)
                    .productItem(cartItem.getProductItem())
                    .productName(cartItem.getProductName())
                    .productImgUrl(cartItem.getProductImgUrl())
                    .productOptionName(cartItem.getProductOptionName_1())
                    .productOptionValue(cartItem.getProductOptionValue_1())
                    .quantity(quantity)
                    .price(cartItem.getOriginalPrice())
                    .build();
        }
    }

    public Order toEntity(Member member, String orderNumber, int orderCount, int totalAmount,
            String orderStatus) {
        return Order.builder()
                .member(member)
                .paymentType(paymentType)
                .orderStatus(orderStatus)
                .orderNumber(orderNumber)
                .orderCount(orderCount)
                .totalAmount(totalAmount)
                .build();
    }

    public OrderHistory toEntity(Order order, CreatedByType createdByType, long createdById,
            String orderStatus) {
        return OrderHistory.builder()
                .order(order)
                .orderStatus(orderStatus)
                .createdByType(createdByType)
                .createdById(createdById)
                .latestVersion(true)
                .build();
    }
}
