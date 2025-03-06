package com.tutti.server.core.payment.domain;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "payment_methods")
public class PaymentMethod extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "method_Type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodType methodType; // 카드, 계좌이체, 카카오페이 등

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentProvider provider; // KB 국민은행, 우리은행, 카카오페이

    @Column(name = "is_default", nullable = false)
    private boolean isDefault; //기본 결제 수단 여부

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodStatus status; // ACTIVE, EXPIRED, DISABLED
}
