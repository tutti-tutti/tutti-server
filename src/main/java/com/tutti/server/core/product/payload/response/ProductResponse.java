package com.tutti.server.core.product.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.store.domain.Store;
import java.time.LocalDateTime;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt

) {

  public static ProductResponse fromEntity(Product product, ProductItem productItem, Store store) {
    return ProductResponse.builder()
        .productId(product.getId())
        .storeName(store.getName())
        .name(product.getName())
        .titleUrl(product.getTitleUrl())
        .description(product.getDescription())
        .originalPrice(productItem.getOriginalPrice())
        .sellingPrice(productItem.getSellingPrice())
        .adultOnly(product.isAdultOnly())
        .likes(product.getLikeCount())
        .build();
  }
}
