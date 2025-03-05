package com.tutti.server.core.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_category_maps")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ProductCategoryMap.ProductCategoryMapId.class)
public class ProductCategoryMap {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "delete_status", nullable = false)
    private boolean deleteStatus = false;

    @Builder
    public ProductCategoryMap(ProductCategory category, Product product, boolean deleteStatus) {
        this.category = category;
        this.product = product;
        this.deleteStatus = deleteStatus;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ProductCategoryMapId implements java.io.Serializable {
        private Long category;
        private Long product;
    }
}