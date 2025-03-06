package com.tutti.server.core.payment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "payment_histories")
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment; // 결제 id

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus paymentStatus; //결제 대기 중 -> 결제 완료

    @Column(name = "latest_status")
    private boolean latestStatus;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime statusUpdatedAt; // 결제 상태 변경 일시
}
