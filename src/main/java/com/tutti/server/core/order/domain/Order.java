package com.tutti.server.core.order.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    // MemberEntity 만들어지면 수정할게요
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member;

    // PaymentEntity 만들어지면 수정할게요
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "payment_id", nullable = false)
//    private Payment payment;

    private int deliveryFee;

    private int totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private OrderStatus orderStatus;

    private LocalDateTime completed_at;

    private long discount;

    @Builder
    public Order(int deliveryFee, int totalAmount, OrderStatus orderStatus,
            LocalDateTime completed_at) {
        this.deliveryFee = deliveryFee;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.completed_at = completed_at;
    }
}
