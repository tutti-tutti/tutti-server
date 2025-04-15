package com.tutti.server.core.cart.domain;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.support.entity.BaseEntity;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
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
    private String firstOptionName;
    private String firstOptionValue;
    private String secondOptionName;
    private String secondOptionValue;

    @Column(columnDefinition = "integer default 1")
    private int quantity;

    private int originalPrice;
    private int sellingPrice;
    private boolean soldOut;

    @Builder
    public CartItem(Member member, ProductItem productItem, String productName,
            String productImgUrl, String firstOptionName, String firstOptionValue,
            String secondOptionName, String secondOptionValue, int quantity, int originalPrice,
            int sellingPrice, boolean soldOut) {
        this.member = member;
        this.productItem = productItem;
        this.productName = productName;
        this.productImgUrl = productImgUrl;
        this.firstOptionName = firstOptionName;
        this.firstOptionValue = firstOptionValue;
        this.secondOptionName = secondOptionName;
        this.secondOptionValue = secondOptionValue;
        this.quantity = quantity;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.soldOut = soldOut;
    }

    public void updateQuantity(int quantity) {
        // 재고에 따라 달라질 수 있는 데이터 일관성 문제로 필드 추가보다는 직접 꺼내 쓰는 방식으로 진행
        final int MAX_QUANTITY = this.productItem.getProduct().getMaxQuantity();

        int newQuantity = this.quantity + quantity;

        // 현재 수량에 새로운 수량을 더한 값이 최대 수량을 초과하면 예외를 던짐
        if (MAX_QUANTITY < newQuantity) {
            throw new DomainException(ExceptionType.EXCEEDS_MAX_QUANTITY);
        }

        this.quantity = newQuantity;
    }
}
