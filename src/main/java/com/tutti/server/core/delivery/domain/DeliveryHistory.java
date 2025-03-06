package com.tutti.server.core.delivery.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "delivery_histories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryHistory {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @Column(length = 50, nullable = false)
    private DeliveryStatus deliveryStatus;

    @Column(length = 100)
    private String note;

    private boolean latestVersion;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public DeliveryHistory(long id, Delivery delivery, DeliveryStatus deliveryStatus, String note,
            boolean latestVersion) {
        this.id = id;
        this.delivery = delivery;
        this.deliveryStatus = deliveryStatus;
        this.note = note;
        this.latestVersion = latestVersion;
        this.createdAt = LocalDateTime.now();
    }
}
