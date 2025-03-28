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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, length = 50)
    private String orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CreatedByType createdByType;

    private long createdById;
    private boolean latestVersion;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public OrderHistory(Order order, String orderStatus, CreatedByType createdByType,
            long createdById, boolean latestVersion) {
        this.order = order;
        this.orderStatus = orderStatus;
        this.createdByType = createdByType;
        this.createdById = createdById;
        this.latestVersion = latestVersion;
    }
}
