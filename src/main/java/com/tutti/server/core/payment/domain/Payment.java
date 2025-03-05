package com.tutti.server.core.payment.domain;


import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private int paidAmount;

    private LocalDateTime completedAt;

    @Column(nullable = false)
    private String tossPaymentKey; // tossPaymentsKey

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member; // 회원 id

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order; // 주문 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentMethod_id", nullable = false)
    private PaymentMethod paymentMethod; // 결제 수단 id

}
