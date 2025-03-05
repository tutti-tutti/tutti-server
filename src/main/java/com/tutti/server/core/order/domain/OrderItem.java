package com.tutti.server.core.order.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // ProductDisplay 엔티티 생기면 바꿀게요
//    @ManyToOne
//    @JoinColumn(name = "product_display_id", nullable = false)
//    private ProductDisplay productDisplay;

    @Column(columnDefinition = "default integer 1")
    private int quantity;

    private int price;

    @Builder
    public OrderItem(Order order, int quantity, int price) {
        this.order = order;
        this.quantity = quantity;
        this.price = price;
    }
}
