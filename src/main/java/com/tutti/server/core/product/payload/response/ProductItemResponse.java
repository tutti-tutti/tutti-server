package com.tutti.server.core.product.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.sku.domain.Sku;
import com.tutti.server.core.store.domain.Store;
import com.tutti.server.core.tag.payload.response.ProductTagResponse;
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
        List<ProductOptionResponse> productOptionItems,
        boolean adultOnly,
        int likes,
        boolean almostOutOfStock,
        List<ProductTagResponse> productTags,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {

    public static ProductItemResponse fromEntity(Product product,
            List<ProductOptionResponse> productOptionItems, Store store, Sku sku
    ) {
        List<ProductTagResponse> tagResponses = product.getProductTags().stream()
                .map(tag -> ProductTagResponse.builder()
                        .tagId(tag.getTag().getId())
                        .tagName(tag.getTag().getTagName())
                        .build())
                .toList();

        return ProductItemResponse.builder()
                .productId(product.getId())
                .storeName(store.getName())
                .name(product.getName())
                .titleUrl(product.getTitleUrl())
                .description(product.getDescription())
                .maxPurchaseQuantity(product.getMaxQuantity())
                .originalPrice(product.getOriginalPrice())
                .productOptionItems(productOptionItems)
                .adultOnly(product.isAdultOnly())
                .likes(product.getLikeCount())
                .almostOutOfStock(sku != null && sku.getStockQuantity() <= 10)
                .productTags(tagResponses)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
