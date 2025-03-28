package com.tutti.server.core.product.domain;

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
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(name = "additional_price")
    @NotNull
    private Integer additionalPrice;

    @Column(name = "discount_price")
    private Integer discountPrice;

    @Column(name = "selling_price")
//    @NotNull
    @Comment("고객 제공 판매가격")
    private Integer sellingPrice;

    @Column(name = "first_option_name", columnDefinition = "TEXT")
    @Comment("색상, 사이즈")
    private String firstOptionName;

    @Column(name = "first_option_value", columnDefinition = "TEXT")
    @Comment("Red, S")
    private String firstOptionValue;

    @Column(name = "second_option_name", columnDefinition = "TEXT")
    @Comment("색상, 사이즈")
    private String secondOptionName;

    @Column(name = "second_option_value", columnDefinition = "TEXT")
    @Comment("Red, S")
    private String secondOptionValue;

    @Column(name = "sold_out")
    @NotNull
    private boolean soldOut;

    // 필수 필드만 포함한 빌더 패턴
    @Builder
    public ProductItem(Product product, Integer additionalPrice, Integer discountPrice,
            Integer sellingPrice, String firstOptionName, String firstOptionValue,
            String secondOptionName, String secondOptionValue, boolean soldOut) {
        this.product = product;
        this.additionalPrice = additionalPrice;
        this.discountPrice = discountPrice;
        this.sellingPrice = sellingPrice;
        this.firstOptionName = firstOptionName;
        this.firstOptionValue = firstOptionValue;
        this.secondOptionName = secondOptionName;
        this.secondOptionValue = secondOptionValue;
        this.soldOut = soldOut;
    }
}
