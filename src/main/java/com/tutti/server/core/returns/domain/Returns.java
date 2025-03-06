package com.tutti.server.core.returns.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "returns")
public class Returns extends BaseEntity {

    @Column(name = "refund_id", nullable = false)
    private Long refundId; // 환불 ID

    @Column(name = "order_id", nullable = false)
    private Long orderId; // 주문 ID

    @Column(name = "carrier_id", nullable = false)
    private Long carrierId; // 택배사 ID

    @Column(name = "quantity", nullable = false)
    private Integer quantity; // 반품 수량

    @Column(name = "reason", length = 255)
    private String reason; // 반품 사유

    @Column(name = "status", length = 50, nullable = false)
    private String status; // 반품 처리 상태

    @Column(name = "expected_return_date")
    private LocalDateTime expectedReturnDate; // 회수 예정일

    @Column(name = "completed_at")
    private LocalDateTime completedAt; // 반품 완료 일시
}
