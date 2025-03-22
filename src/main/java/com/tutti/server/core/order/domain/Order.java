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
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentType;

    @Column(nullable = false, length = 50)
    private String orderStatus;

    private String orderNumber;
    private String orderName;
    private int orderCount;
    private int totalProductAmount;
    private int discountAmount;
    private int deliveryFee;
    private int totalAmount;

    @Comment("결제 완료 일시")
    private LocalDateTime paidAt;
    @Comment("배송 완료 일시")
    private LocalDateTime deliveredAt;
    @Comment("주문 완료 일시")
    private LocalDateTime completedAt;


    @Builder
    public Order(Member member, PaymentMethodType paymentType, String orderStatus,
            String orderNumber, String orderName, int orderCount, int totalProductAmount,
            int discountAmount, int totalAmount, LocalDateTime paidAt, LocalDateTime deliveredAt,
            LocalDateTime completedAt) {
        this.member = member;
        this.paymentType = paymentType;
        this.orderStatus = orderStatus;
        this.orderNumber = orderNumber;
        this.orderName = orderName;
        this.orderCount = orderCount;
        this.totalProductAmount = totalProductAmount;
        this.discountAmount = discountAmount;
        this.deliveryFee = 0;
        this.totalAmount = totalAmount;
        this.paidAt = paidAt;
        this.deliveredAt = deliveredAt;
        this.completedAt = completedAt;
    }
}
