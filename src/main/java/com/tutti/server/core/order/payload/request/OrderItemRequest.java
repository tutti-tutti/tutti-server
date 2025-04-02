package com.tutti.server.core.order.payload.request;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderItem;
import com.tutti.server.core.product.domain.ProductItem;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record OrderItemRequest(

        @NotNull(message = "필수 옵션을 선택해주세요.")
        Long productItemId,

        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        @Max(value = 10, message = "최대 10개까지 주문 가능합니다.")
        int quantity
) {

    public OrderItem toEntity(Order order, ProductItem productItem) {
        var product = productItem.getProduct();
        var store = product.getStoreId();
        var expectedArrivalAt = LocalDate.now().plusDays((int) ((Math.random()) * 7) + 1);

        return OrderItem.builder()
                .order(order)
                .store(store)
                .storeName(store.getName())
                .productItem(productItem)
                .productName(product.getName())
                .productImgUrl(product.getTitleUrl())
                .firstOptionName(productItem.getFirstOptionName())
                .firstOptionValue(productItem.getFirstOptionValue())
                .secondOptionName(productItem.getSecondOptionName())
                .secondOptionValue(productItem.getSecondOptionValue())
                .quantity(quantity)
                .price(productItem.getSellingPrice())
                .expectedArrivalAt(expectedArrivalAt)
                .build();
    }
}
