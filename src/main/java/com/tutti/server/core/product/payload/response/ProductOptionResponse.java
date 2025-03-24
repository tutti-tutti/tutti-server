package com.tutti.server.core.product.payload.response;

import com.tutti.server.core.product.domain.ProductItem;
import lombok.Builder;

@Builder
public record ProductOptionResponse(
        Long productItemId,
        String firstOptionName,
        String firstOptionValue,
        String secondOptionName,
        String secondOptionValue,
        int additionalPrice
) {

    public static ProductOptionResponse from(ProductItem productItem) {
        return ProductOptionResponse.builder()
                .productItemId(productItem.getId())
                .firstOptionName(productItem.getFirstOptionName())
                .firstOptionValue(productItem.getFirstOptionValue())
                .secondOptionName(productItem.getSecondOptionName())
                .secondOptionValue(productItem.getSecondOptionValue())
                .additionalPrice(productItem.getAdditionalPrice())
                .build();
    }
}
