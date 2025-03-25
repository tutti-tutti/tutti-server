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
        int discountPrice,
        int additionalPrice,
        int stockQuantity,
        List<ProductOptionResponse> productItems,
        boolean adultOnly,
        int likes,
        boolean almostOutOfStock,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {

    public static List<ProductItemResponse> fromEntity(Product product,
            List<ProductItem> productItems,
            List<Sku> skus, Store store) {
        // 재고 상태 확인 (하나라도 stockQuantity가 5 이하면 almostOutOfStock = true)
        boolean almostOutOfStock = skus.stream()
                .anyMatch(sku -> sku.getStockQuantity() <= 5);

        // 각 ProductItem마다 ProductItemResponse 생성
        return productItems.stream()
                .map(productItem -> {
                    List<ProductOptionResponse> options = List.of(
                            ProductOptionResponse.from(productItem));

                    return ProductItemResponse.builder()
                            .productId(product.getId())
                            .storeName(store.getName())
                            .name(product.getName())
                            .titleUrl(product.getTitleUrl())
                            .description(product.getDescription())
                            .maxPurchaseQuantity(product.getMaxQuantity())
                            .originalPrice(product.getOriginalPrice())
                            .sellingPrice(
                                    productItem.getSellingPrice())  // 각 productItem의 selling price 사용
                            .discountPrice(productItem.getDiscountPrice())
                            .additionalPrice(productItem.getAdditionalPrice())
                            .productItems(options)
                            .adultOnly(product.isAdultOnly())
                            .likes(product.getLikeCount())
                            .almostOutOfStock(almostOutOfStock)
                            .createdAt(productItem.getCreatedAt())
                            .updatedAt(productItem.getUpdatedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * 단일 ProductItem과 단일 Sku를 받는 fromEntity 메서드
     */
    public static ProductItemResponse fromEntity(Product product, ProductItem productItem, Sku sku,
            Store store) {
        // 단일 ProductItem에 대한 ProductOptionResponse 생성
        List<ProductOptionResponse> options = List.of(
                ProductOptionResponse.from(productItem));
            
        return ProductItemResponse.builder()
                .productId(product.getId())
                .storeName(store.getName())
                .name(product.getName())
                .titleUrl(product.getTitleUrl())
                .description(product.getDescription())
                .maxPurchaseQuantity(product.getMaxQuantity())
                .originalPrice(product.getOriginalPrice())
                .sellingPrice(productItem.getSellingPrice())
                .discountPrice(productItem.getDiscountPrice())
                .additionalPrice(productItem.getAdditionalPrice())
                .stockQuantity(sku.getStockQuantity())
                .productItems(options)  // List<ProductOptionResponse> 설정
                .adultOnly(product.isAdultOnly())
                .likes(product.getLikeCount())
                .almostOutOfStock(sku.getStockQuantity() <= 5)
                .createdAt(productItem.getCreatedAt())
                .updatedAt(productItem.getUpdatedAt())
                .build();
    }


    /**
     * 상품 ID와 ProductItem 리스트를 받아서 통합된 응답을 생성하는 fromEntities 메서드
     */
    public static ProductItemResponse fromEntities(Product product, List<ProductItem> productItems,
            List<Sku> skus, Store store) {
        // 재고 상태 확인 (하나라도 stockQuantity가 5 이하면 almostOutOfStock = true)
        boolean almostOutOfStock = skus.stream()
                .anyMatch(sku -> sku.getStockQuantity() <= 5);
            
        // 각 ProductItem에 대한 ProductOptionResponse 목록 생성
        List<ProductOptionResponse> options = productItems.stream()
                .map(ProductOptionResponse::from)
                .collect(Collectors.toList());

        // 대표 정보를 위해 첫 번째 ProductItem 사용 (또는 기준으로 삼을 상품)
        ProductItem representativeItem = productItems.isEmpty() ? null : productItems.get(0);
        
        return ProductItemResponse.builder()
                .productId(product.getId())
                .storeName(store.getName())
                .name(product.getName())
                .titleUrl(product.getTitleUrl())
                .description(product.getDescription())
                .maxPurchaseQuantity(product.getMaxQuantity())
                .originalPrice(product.getOriginalPrice())
                // 가격 정보는 대표 상품 정보로 설정 (기준 가격)
                .sellingPrice(product.getOriginalPrice()) // 또는 기본 판매가로 설정
                .discountPrice(0) // 기본 할인가
                .additionalPrice(0) // 기본 추가가격
                .stockQuantity(0) // 전체 상품이므로 총 재고는 표시하지 않음
                .productItems(options) // 모든 ProductItem 옵션 정보를 포함
                .adultOnly(product.isAdultOnly())
                .likes(product.getLikeCount())
                .almostOutOfStock(almostOutOfStock)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
