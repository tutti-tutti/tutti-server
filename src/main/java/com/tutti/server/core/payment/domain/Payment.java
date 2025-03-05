package com.tutti.server.core.payment.domain;


import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Column(nullable = false)
    private String orderName;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private int discountAmount;

    @Column(nullable = false)
    private int paidAmount;

    private LocalDateTime completedAt;

    @Column(nullable = false)
    private String tossPaymentKey; // tossPaymentsKey

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member; // 회원 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // 주문 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentMethod_id", nullable = false)
    private PaymentMethod paymentMethod; // 결제 수단 id
}