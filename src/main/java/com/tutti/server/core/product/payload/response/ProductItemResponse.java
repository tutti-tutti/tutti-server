package com.tutti.server.core.product.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.stock.domain.Sku;
import com.tutti.server.core.store.domain.Store;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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

    public static ProductItemResponse fromEntity(Product product, List<ProductItem> productItems,
            List<Sku> skus, Store store) {
        // ProductItem들의 옵션 정보를 ProductOptionResponse로 변환
        List<ProductOptionResponse> options = productItems.stream()
                .map(ProductOptionResponse::from)
                .collect(Collectors.toList());

        // 재고 상태 확인 (하나라도 stockQuantity가 5 이하면 almostOutOfStock = true)
        boolean almostOutOfStock = skus.stream()
                .anyMatch(sku -> sku.getStockQuantity() <= 5);

        return ProductItemResponse.builder()
                .productId(product.getId())
                .storeName(store.getName())
                .name(product.getName())
                .titleUrl(product.getTitleUrl())
                .description(product.getDescription())
                .maxPurchaseQuantity(product.getMaxQuantity())
                .originalPrice(product.getOriginalPrice())
                .sellingPrice(product.getOriginalPrice())
                .options(options)
                .adultOnly(product.isAdultOnly())
                .likes(product.getLikeCount())
                .almostOutOfStock(almostOutOfStock)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

}
