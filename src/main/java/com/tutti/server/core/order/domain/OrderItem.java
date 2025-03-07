package com.tutti.server.core.order.domain;

import com.tutti.server.core.product.domain.ProductItem;
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
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    private int quantity;

    private int price;

    @Builder
    public OrderItem(Order order, ProductItem productItem, int quantity, int price) {
        this.order = order;
        this.productItem = productItem;
        this.quantity = quantity;
        this.price = price;
    }
}
