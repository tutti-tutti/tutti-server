package com.tutti.server.core.product.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.store.domain.Store;
import com.tutti.server.core.tag.domain.ProductTag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

@Builder
public record ProductResponse(

        Long productId,
        String storeName,
        String name,
        String titleUrl,
        String description,
        Integer originalPrice,
        int sellingPrice,
        boolean adultOnly,
        int likes,
        List<ProductTag> productTags,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt

) {

    public static ProductResponse fromEntity(Product product, ProductItem productItem,
            Store store
    ) {
        List<ProductTag> productTags = new ArrayList<>(product.getProductTags());

        return ProductResponse.builder()
                .productId(product.getId())
                .storeName(store.getName())
                .name(product.getName())
                .titleUrl(product.getTitleUrl())
                .description(product.getDescription())
                .originalPrice(product.getOriginalPrice())
                .sellingPrice(productItem.getSellingPrice())
                .adultOnly(product.isAdultOnly())
                .likes(product.getLikeCount())
                .productTags(productTags)
                .createdAt(product.getCreatedAt())
                .build();
    }
}
