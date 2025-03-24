package com.tutti.server.core.product.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.store.domain.Store;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record ProductItemResponse(
        Long productId,
        String storeName,
        String name,
        String titleUrl,
        String description,
        int maxPurchaseQuantity,
        int originalPrice,
        int sellingPrice,
        List<ProductOptionResponse> options,
        boolean adultOnly,
        int likes,
        boolean almostOutOfStock,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {

    // skus
    // sku_histories
    // options, almostOutOfStock 따로처리
    public static ProductItemResponse from(ProductItem productItem, Product product, Store store) {
        return ProductItemResponse.builder()
                .productId(product.getId())
                .storeName(store.getName())
                .name(product.getName())
                .titleUrl(product.getTitleUrl())
                .description(product.getDescription())
                .maxPurchaseQuantity(product.getMaxQuantity())
                .originalPrice(product.getOriginalPrice())
                .sellingPrice(productItem.getSellingPrice())
                .adultOnly(product.isAdultOnly())
                .likes(product.getLikeCount())
                .build();
    }

}
