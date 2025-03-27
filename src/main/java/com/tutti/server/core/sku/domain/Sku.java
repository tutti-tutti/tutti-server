package com.tutti.server.core.sku.domain;

import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skus")
@Getter
@NoArgsConstructor
public class Sku extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "sku_id", nullable = false)
//    private Long skuId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id")
    private ProductItem productItem;

    @Column(name = "sku_code", nullable = false)
    private String skuCode;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;

    private String generateSkuCode(String category, int sequence) {
        return category.toUpperCase() + "-" + String.format("%03d", sequence);
    }

    @Builder
    public Sku(ProductItem productItem, String skuCode, int stockQuantity) {
        this.productItem = productItem;
        this.skuCode = skuCode;
        this.stockQuantity = stockQuantity;
    }
}
