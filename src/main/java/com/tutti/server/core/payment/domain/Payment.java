package com.tutti.server.core.payment.domain;


import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment")
public class Payment extends BaseEntity {

    @Column(nullable = false)
    private String orderName;

    @Column(nullable = false)
    private BigDecimal amount;

    //TODO:  enum테이블 정의하기
//    @Column(nullable = false)
//    private PaymentStatus status;

    private BigDecimal discountAmount;

    @Column(nullable = false)
    private BigDecimal paidAmount;

    private LocalDateTime completed_at;

    @Column(nullable = false)
    private String tossPaymentKey;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<PaymentHistory> paymentHistoryList = new ArrayList<>(); // 결제 이력


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 회원 id


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // 주문 id
}
