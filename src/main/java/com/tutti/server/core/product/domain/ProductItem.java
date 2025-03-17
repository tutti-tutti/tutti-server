package com.tutti.server.core.product.domain;

import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tutti.server.core.support.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_items")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @Column(name = "original_price")
    private Integer originalPrice;

    @Column(name = "selling_price")
    @NotNull
    private Integer sellingPrice;

    @Column(name = "option_name", columnDefinition = "TEXT")
    @Comment("색상, 사이즈")
    private String optionName;

    @Column(name = "option_value", columnDefinition = "TEXT")
    @Comment("Red, S")
    private String optionValue;

    @Column(name = "sold_out")
    @NotNull
    private boolean soldOut;

    // 필수 필드만 포함한 빌더 패턴
    @Builder
    public ProductItem(Product product, Integer originalPrice, Integer sellingPrice,
            String optionName, String optionValue, boolean soldOut) {
        this.product = product;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.optionName = optionName;
        this.optionValue = optionValue;
        this.soldOut = soldOut;
    }
}
