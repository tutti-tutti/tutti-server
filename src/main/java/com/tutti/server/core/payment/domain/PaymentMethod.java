package com.tutti.server.core.payment.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod extends BaseEntity {

    //TODO: 머지했을 떄 충돌 여부 확인
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "payment_id", nullable = false)
//    private Member member;

    @Column(nullable = false, length = 100)
    private String methodType;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private Boolean isDefault;

    //TODO: enum으로 교체하기
//    @Column(nullable = false, length = 50)
//    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
}
