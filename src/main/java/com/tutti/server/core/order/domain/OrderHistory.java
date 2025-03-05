package com.tutti.server.core.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Table(name = "order_histories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistory {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CreatedByType createdByType;

    private long createdById;

    @Column(length = 100)
    private String cancelReason;

    private boolean latestVersion;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public OrderHistory(long id, Order order, OrderStatus orderStatus, CreatedByType createdByType,
            String cancelReason, boolean latestVersion) {
        this.id = id;
        this.order = order;
        this.orderStatus = orderStatus;
        this.createdByType = createdByType;
        this.cancelReason = cancelReason;
        this.latestVersion = latestVersion;
        this.createdAt = LocalDateTime.now();
    }
}
