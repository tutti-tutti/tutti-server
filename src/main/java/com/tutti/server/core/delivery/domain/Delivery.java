package com.tutti.server.core.delivery.domain;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "deliveries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier;

    @Column(length = 50)
    private String trackingNumber;

    @Column(length = 50, nullable = false)
    private DeliveryStatus deliveryStatus;

    private LocalDate expectedAt;
    private LocalDateTime departedAt;
    private LocalDateTime deliveredAt;

    @Column(length = 50)
    private String destinationName;
    private String recipientAddress;

    @Column(length = 50)
    private String recipientName;

    @Column(length = 20)
    private String recipientPhone;

    @Column(length = 20)
    private String zipcode;

    private String address;
    private String addressDetail;
    private String note;

    @Builder
    public Delivery(Order order, Carrier carrier, String trackingNumber,
            DeliveryStatus deliveryStatus, LocalDate expectedAt, LocalDateTime departedAt,
            LocalDateTime deliveredAt, String destinationName, String recipientAddress,
            String recipientName, String recipientPhone, String zipcode, String address,
            String addressDetail, String note) {
        this.order = order;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.deliveryStatus = deliveryStatus;
        this.expectedAt = expectedAt;
        this.departedAt = departedAt;
        this.deliveredAt = deliveredAt;
        this.destinationName = destinationName;
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.note = note;
    }
}
