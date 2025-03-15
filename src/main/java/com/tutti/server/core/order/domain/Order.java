package com.tutti.server.core.order.domain;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private int deliveryFee;
    private int totalAmount;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentType;

    @Column(nullable = false, length = 50)
    private String orderStatus;

    private LocalDateTime completed_at;
    private int orderCount;

    private String orderNumber;

    @Builder
    public Order(Member member, int deliveryFee, int totalAmount, PaymentMethodType paymentType,
            String orderStatus, LocalDateTime completed_at, int orderCount) {
        this.member = member;
        this.deliveryFee = deliveryFee;
        this.totalAmount = totalAmount;
        this.paymentType = paymentType;
        this.orderStatus = orderStatus;
        this.completed_at = completed_at;
        this.orderCount = orderCount;
    }
}
