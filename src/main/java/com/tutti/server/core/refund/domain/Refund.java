package com.tutti.server.core.refund.domain;


import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refunds")
public class Refund extends BaseEntity {

    @Column(nullable = false)
    private int amount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus;

    @Column(name = "method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodType refundMethod; // 환불수단

    @Column(name = "fee")
    private int returnFee; // 환불배송

    @Column(name = "completed_at")
    private LocalDateTime refundCompletedAt; // 환불완료일시

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Refund(int amount, RefundStatus refundStatus, PaymentMethodType refundMethod,
            int returnFee, Payment payment, Member member) {
        this.amount = amount;
        this.refundStatus = refundStatus;
        this.refundMethod = refundMethod;
        this.returnFee = returnFee;
        this.payment = payment;
        this.member = member;
    }

    public void completeRefund() {
        if (this.refundStatus == RefundStatus.REFUND_COMPLETED) {
            throw new IllegalStateException("이미 환불이 완료되었습니다.");
        }
        this.refundStatus = RefundStatus.REFUND_COMPLETED;
        this.refundCompletedAt = LocalDateTime.now();
    }
}
