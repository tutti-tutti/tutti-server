package com.tutti.server.core.cart.domain;

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
@Table(name = "cart_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // ProductDisplay 엔티티 생기면 추가할게요
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_display_id", nullable = false)
//    private ProductDisplay productDisplay;

    @Column(columnDefinition = "integer default 1")
    private int quantity;

    private int price;

    private boolean soldOut;

    @Builder
    public CartItem(Cart cart, int quantity, int price, boolean soldOut) {
        this.cart = cart;
        this.quantity = quantity;
        this.price = price;
        this.soldOut = soldOut;
    }
}
