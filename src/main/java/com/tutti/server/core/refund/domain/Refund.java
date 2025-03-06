package com.tutti.server.core.refund.domain;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentMethodType;
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
@Table(name = "refunds")
public class Refund extends BaseEntity {

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodType refundMethod; // 환불수단

    @Column
    private int refundFee; // 환불배송

    @Column
    private LocalDateTime refundCompletedAt; // 환불완료일시

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Refund(int amount, RefundStatus refundStatus, PaymentMethodType refundMethod,
            int refundFee, Payment payment, Member member) {
        this.amount = amount;
        this.refundStatus = refundStatus;
        this.refundMethod = refundMethod;
        this.refundFee = refundFee;
        this.payment = payment;
        this.member = member;
    }

    public void completeRefund() { // TODO: 임시
        if (this.refundStatus == RefundStatus.COMPLETED) {
            throw new IllegalStateException("이미 환불이 완료된 상태입니다.");
        }
        this.refundStatus = RefundStatus.COMPLETED;
        this.refundCompletedAt = LocalDateTime.now();
    }
}
