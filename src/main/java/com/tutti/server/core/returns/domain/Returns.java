package com.tutti.server.core.returns.domain;

import com.tutti.server.core.delivery.domain.Carrier;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.refund.domain.Refund;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private int quantity; // 반품 수량

    @Column
    private String reason; // 반품 사유

    @Column(name = "status", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private ReturnStatus returnStatus; // 반품 처리 상태

    @Column(name = "expected_return_date")
    private LocalDate expectedReturnDate; // 회수 예정일

    @Column(name = "completed_at")
    private LocalDateTime completedAt; // 반품 완료 일시

    @Builder
    public Returns(Refund refund, Order order, Carrier carrier, int quantity, String reason,
            ReturnStatus returnStatus,
            LocalDate expectedReturnDate, LocalDateTime completedAt) {
        this.refund = refund;
        this.order = order;
        this.carrier = carrier;
        this.quantity = quantity;
        this.reason = reason;
        this.returnStatus = returnStatus;
        this.expectedReturnDate = expectedReturnDate;
        this.completedAt = completedAt;
    }
}
