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

    @Comment("고객 확인용 주문 번호")
    private String orderNumber;
    private String orderName;

    @Comment("orderItem 별 건수 (수량 아님)")
    private int orderCount;

    @Comment("할인 금액")
    private int totalDiscountAmount;

    @Comment("총 상품 금액")
    private int totalProductAmount;

    @Comment("배송비")
    private int deliveryFee;

    @Comment("총 결제 금액")
    private int totalAmount;

    @Comment("주문 완료 일시")
    private LocalDateTime completedAt;

    @Comment("결제 완료 일시")
    private LocalDateTime paidAt;

    @Comment("배송 완료 일시")
    private LocalDateTime deliveredAt;


    @Builder
    public Order(Member member, PaymentMethodType paymentType, String orderStatus,
            String orderNumber, String orderName, int orderCount, int totalDiscountAmount,
            int totalProductAmount, int deliveryFee, int totalAmount, LocalDateTime completedAt,
            LocalDateTime deliveredAt, LocalDateTime paidAt) {
        this.member = member;
        this.paymentType = paymentType;
        this.orderStatus = orderStatus;
        this.orderNumber = orderNumber;
        this.orderName = orderName;
        this.orderCount = orderCount;
        this.totalDiscountAmount = totalDiscountAmount;
        this.totalProductAmount = totalProductAmount;
        this.deliveryFee = deliveryFee;
        this.totalAmount = totalAmount;
        this.completedAt = completedAt;
        this.paidAt = paidAt;
        this.deliveredAt = deliveredAt;
    }

    public void updateCompletedAt(LocalDateTime completed) {
        this.paidAt = completed;
    }


    public void updatePaidAt(LocalDateTime paid) {
        this.paidAt = paid;
    }

    public void updateDeliveredAt(LocalDateTime delivered) {
        this.deliveredAt = delivered;
    }
}
