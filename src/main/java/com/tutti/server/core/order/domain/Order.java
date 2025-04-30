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
    @Comment("주문한 사용자")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @Comment("결제 수단")
    private PaymentMethodType paymentType;

    @Column(nullable = false, length = 50)
    @Comment("주문 상태")
    private String orderStatus;

    @Comment("고객 확인용 주문 번호")
    private String orderSheetNo;

    @Comment("주문명")
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

    @Comment("결제 완료 일시")
    private LocalDateTime paidAt;

    @Comment("배송 완료 일시")
    private LocalDateTime deliveredAt;

    @Comment("주문 완료 일시")
    private LocalDateTime completedAt;


    @Builder
    public Order(Member member, PaymentMethodType paymentType, String orderStatus,
            String orderSheetNo, String orderName, int orderCount, int totalDiscountAmount,
            int totalProductAmount, int deliveryFee, int totalAmount, LocalDateTime deliveredAt,
            LocalDateTime paidAt, LocalDateTime completedAt) {
        this.member = member;
        this.paymentType = paymentType;
        this.orderStatus = orderStatus;
        this.orderSheetNo = orderSheetNo;
        this.orderName = orderName;
        this.orderCount = orderCount;
        this.totalDiscountAmount = totalDiscountAmount;
        this.totalProductAmount = totalProductAmount;
        this.deliveryFee = deliveryFee;
        this.totalAmount = totalAmount;
        this.paidAt = paidAt;
        this.deliveredAt = deliveredAt;
        this.completedAt = completedAt;
    }

    public void updateOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
