package com.tutti.server.core.order.domain;

import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.store.domain.Store;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String storeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    private String productName;
    private String productImgUrl;
    private String firstOptionName;
    private String firstOptionValue;
    private String secondOptionName;
    private String secondOptionValue;
    private int quantity;
    private int price;

    @Builder
    public OrderItem(Order order, Store store, String storeName, ProductItem productItem,
            String productName, String productImgUrl, String firstOptionName,
            String firstOptionValue, String secondOptionName, String secondOptionValue,
            int quantity, int price) {
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
    }
}
