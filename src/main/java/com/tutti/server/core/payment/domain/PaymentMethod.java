package com.tutti.server.core.payment.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "payment_methods")
public class PaymentMethod extends BaseEntity {

    //TODO: 머지했을 떄 충돌 여부 확인
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "payment_id", nullable = false)
//    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodType methodType; // 카드, 계좌이체, 카카오페이 등

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentProvider provider; // KB 국민은행, 우리은행, 카카오페이

    @Column(nullable = false)
    private boolean isDefault; //기본 결제 수단 여부

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodStatus status; // ACTIVE, EXPIRED, DISABLED
}
