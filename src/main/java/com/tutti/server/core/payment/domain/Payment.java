package com.tutti.server.core.payment.domain;


import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Column(nullable = false)
    private String orderName;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String paymentStatus;

    private LocalDateTime completedAt;

    @Column
    private String tossPaymentKey; // tossPaymentsKey

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 회원 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // 주문 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id") // 결제 요청이 왔을 때는 몰라도 됨.
    private PaymentMethod paymentMethod; // 결제 수단 id

    @Column
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethodType paymentMethodType; // 결제 수단 타입

    @Builder
    public Payment(String orderName, int amount, String paymentStatus,
            String tossPaymentKey, Member member,
            Order order, PaymentMethod paymentMethod, String orderNumber,
            PaymentMethodType paymentMethodType) {

        this.orderName = orderName;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.tossPaymentKey = tossPaymentKey;
        this.member = member;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.orderNumber = orderNumber;
        this.paymentMethodType = paymentMethodType;
    }

    // 결제 승인 후 PaymentKey 저장하는 방식으로 변경.
    public void completePayment(String tossPaymentKey) {
        if (this.paymentStatus.equals(PaymentStatus.DONE.name())) {
            throw new IllegalStateException("이미 결제가 완료된 주문입니다.");
        }
        this.paymentStatus = PaymentStatus.DONE.name();
        this.tossPaymentKey = tossPaymentKey;
        this.completedAt = LocalDateTime.now();
    }

    // Toss 결제 승인 후 상태 업데이트
    public void confirmPayment(PaymentMethod paymentMethod, String tossPaymentKey,
            String status,
            LocalDateTime completedAt, int amount) {
        this.paymentMethod = paymentMethod;
        // paymentKey, paymentStatus, approvedAt, amount 등도 함께 업데이트
        this.tossPaymentKey = tossPaymentKey;
        this.paymentStatus = status;
        this.completedAt = completedAt;
        this.amount = amount;
    }

    public void cancelPayment(LocalDateTime canceledAt) {
        this.paymentStatus = PaymentStatus.CANCELED.name();
        this.completedAt = canceledAt;
    }
}
