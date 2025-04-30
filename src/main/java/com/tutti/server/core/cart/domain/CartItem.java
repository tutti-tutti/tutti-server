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
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "cart_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Comment("장바구니 주인 ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    @Comment("장바구니에 추가된 상품 ID")
    private ProductItem productItem;

    @Comment("상품명")
    private String productName;

    @Comment("상품 이미지 URL")
    private String productImgUrl;

    @Comment("상품 옵션명 1")
    private String firstOptionName;

    @Comment("상품 옵션값 1")
    private String firstOptionValue;

    @Comment("상품 옵션명 2")
    private String secondOptionName;

    @Comment("상품 옵션값 2")
    private String secondOptionValue;

    @Column(columnDefinition = "integer default 1")
    @Comment("담긴 상품 수량")
    private int quantity;

    @Comment("원가 = 부모 상품 원가 + 옵션 추가금")
    private int originalPrice;

    @Comment("실제 구매하는 가격 = 원가 - 할인 금액")
    private int sellingPrice;

    @Comment("품절 여부")
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
