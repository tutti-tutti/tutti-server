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
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    private String productName;
    private String productImgUrl;
    private String productOptionName_1;
    private String productOptionValue_1;
    private String productOptionName_2;
    private String productOptionValue_2;
    private int quantity;
    private int sellingPrice;

    @Builder
    public OrderItem(Order order, ProductItem productItem, String productName, String productImgUrl,
            String productOptionName_1, String productOptionValue_1, String productOptionName_2,
            String productOptionValue_2, int quantity, int sellingPrice) {
        this.order = order;
        this.productItem = productItem;
        this.productName = productName;
        this.productImgUrl = productImgUrl;
        this.productOptionName_1 = productOptionName_1;
        this.productOptionValue_1 = productOptionValue_1;
        this.productOptionName_2 = productOptionName_2;
        this.productOptionValue_2 = productOptionValue_2;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
    }
}
