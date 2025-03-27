package com.tutti.server.core.product.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutti.server.core.product.domain.Product;
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
        List<ProductOptionResponse> productItems,
        boolean adultOnly,
        int likes,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {

    /**
     * 단일 ProductItem과 단일 Sku를 받는 fromEntity 메서드
     */
    public static ProductItemResponse fromEntity(Product product,
            List<ProductOptionResponse> productItems, Store store) {

        return ProductItemResponse.builder()
                .productId(product.getId())
                .storeName(store.getName())
                .name(product.getName())
                .titleUrl(product.getTitleUrl())
                .description(product.getDescription())
                .maxPurchaseQuantity(product.getMaxQuantity())
                .originalPrice(product.getOriginalPrice())
//                .stockQuantity(sku.getStockQuantity())
                .productItems(productItems)  // List<ProductOptionResponse> 설정
                .adultOnly(product.isAdultOnly())
                .likes(product.getLikeCount())
//                .almostOutOfStock(sku.getStockQuantity() <= 5)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
