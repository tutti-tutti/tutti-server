package com.tutti.server.core.order.domain;

import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.store.domain.Store;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Comment("주문 그룹 ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @Comment("스토어 ID")
    private Store store;

    @Comment("스토어명")
    private String storeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    @Comment("주문한 옵션별 상품 ID")
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

    @Comment("주문 수량")
    private int quantity;

    @Comment("주문 당시 가격")
    private int price;

    @Comment("예상 도착일")
    private LocalDate expectedArrivalAt;

    @Builder
    public OrderItem(Order order, Store store, String storeName, ProductItem productItem,
            String productName, String productImgUrl, String firstOptionName,
            String firstOptionValue, String secondOptionName, String secondOptionValue,
            int quantity, int price, LocalDate expectedArrivalAt) {
        this.order = order;
        this.store = store;
        this.storeName = storeName;
        this.productItem = productItem;
        this.productName = productName;
        this.productImgUrl = productImgUrl;
        this.firstOptionName = firstOptionName;
        this.firstOptionValue = firstOptionValue;
        this.secondOptionName = secondOptionName;
        this.secondOptionValue = secondOptionValue;
        this.quantity = quantity;
        this.price = price;
        this.expectedArrivalAt = expectedArrivalAt;
    }
}
