package com.tutti.server.core.product.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "product_item")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_price")
    private Integer originalPrice;

    @Column(name = "selling_price", nullable = false)
    private Integer sellingPrice;

    // Product와 다대일(N:1) 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Attribute와 다대일(N:1) 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @Builder
    public ProductItem(Integer originalPrice, Integer sellingPrice, Product product) {
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.product = product;
    }
}