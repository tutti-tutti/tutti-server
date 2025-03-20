package com.tutti.server.core.cart.domain;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cart_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    private String productName;
    private String productImgUrl;
    private String productOptionName_1;
    private String productOptionValue_1;
    private String productOptionName_2;
    private String productOptionValue_2;

    @Column(columnDefinition = "integer default 1")
    private int quantity;

    private int originalPrice;
    private int sellingPrice;
    private boolean soldOut;

    @Builder
    public CartItem(Member member, ProductItem productItem, String productName,
            String productImgUrl, String productOptionName_1, String productOptionValue_1,
            String productOptionName_2, String productOptionValue_2, int quantity,
            int originalPrice, int sellingPrice, boolean soldOut) {
        this.member = member;
        this.productItem = productItem;
        this.productName = productName;
        this.productImgUrl = productImgUrl;
        this.productOptionName_1 = productOptionName_1;
        this.productOptionValue_1 = productOptionValue_1;
        this.productOptionName_2 = productOptionName_2;
        this.productOptionValue_2 = productOptionValue_2;
        this.quantity = quantity;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.soldOut = soldOut;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}
