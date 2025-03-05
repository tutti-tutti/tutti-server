package com.tutti.server.core.product.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "product_item")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_item_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "original_price")
    private Integer originalPrice;

    @Column(name = "selling_price", nullable = false)
    private Integer sellingPrice;

    @Column(name = "options", columnDefinition = "TEXT")
    @Comment("색상=Red, 사이즈=S")
    private String options;

    // 필수 필드만 포함한 빌더 패턴
    @Builder
    public ProductItem(Product product, Integer originalPrice, Integer sellingPrice, String options) {
        this.product = product;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.options = options;

    }
}