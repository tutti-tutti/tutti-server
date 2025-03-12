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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private LocalDateTime completedAt;

    @Column
    private String tossPaymentKey; // tossPaymentsKey

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 회원 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // 주문 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id") // 결제 요청이 왔을 때는 몰라도 됨.
    private PaymentMethod paymentMethod; // 결제 수단 id

    @Builder
    public Payment(String orderName, int amount, PaymentStatus paymentStatus,
            String tossPaymentKey, Member member,
            Order order, PaymentMethod paymentMethod) {
        this.orderName = orderName;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.tossPaymentKey = tossPaymentKey;
        this.member = member;
        this.order = order;
        this.paymentMethod = paymentMethod;
    }

    // 결제 승인 후 PaymentKey 저장하는 방식으로 변경.
    public void completePayment(String tossPaymentKey) {
        if (this.paymentStatus == PaymentStatus.PAYMENT_COMPLETED) {
            throw new IllegalStateException("이미 결제가 완료된 주문입니다.");
        }
        this.paymentStatus = PaymentStatus.PAYMENT_COMPLETED;
        this.tossPaymentKey = tossPaymentKey;
        this.completedAt = LocalDateTime.now();
    }

    // 요청이 들어왔을때 첫 결제가 생성됨.
    public static Payment createPayment(Order order, Member member, int amount, String orderName) {
        return Payment.builder()
                .orderName(orderName)
                .amount(amount)
                .paymentStatus(PaymentStatus.PAYMENT_REQUESTED)
                .tossPaymentKey(null)
                .member(member)
                .order(order)
                .paymentMethod(null)
                .build();
    }
}
