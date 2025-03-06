package com.tutti.server.core.returns.domain;

import com.tutti.server.core.delivery.domain.Carrier;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.refund.domain.Refund;
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
@Table(name = "returns")
public class Returns extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refund_id", nullable = false)
    private Refund refund; // 환불 엔티티 참조

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // 주문 엔티티 참조

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier; // 택배사 엔티티 참조

    @Column(name = "quantity", nullable = false)
    private int quantity; // 반품 수량

    @Column(name = "reason", length = 255)
    private String reason; // 반품 사유

    @Column(name = "status", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private ReturnStatus status; // 반품 처리 상태

    @Column(name = "expected_return_date")
    private LocalDateTime expectedReturnDate; // 회수 예정일

    @Column(name = "completed_at")
    private LocalDateTime completedAt; // 반품 완료 일시

    @Builder
    public Returns(Refund refund, Order order, Carrier carrier, int quantity, String reason, ReturnStatus status,
                   LocalDateTime expectedReturnDate, LocalDateTime completedAt) {
        this.refund = refund;
        this.order = order;
        this.carrier = carrier;
        this.quantity = quantity;
        this.reason = reason;
        this.status = status;
        this.expectedReturnDate = expectedReturnDate;
        this.completedAt = completedAt;
    }
}
