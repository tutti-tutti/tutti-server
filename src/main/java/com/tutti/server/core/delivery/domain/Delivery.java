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

    @Column(length = 50)
    private String carrierName;

    @Column(length = 20)
    private String carrierContact;

    @Column(length = 50)
    private String trackingNumber;

    @Column(length = 50)
    private DeliveryStatus deliveryStatus;

    private LocalDate expectedAt;
    private LocalDateTime departedAt;
    private LocalDateTime deliveredAt;

    @Column(length = 50)
    private String destinationName;

    @Column(length = 50)
    private String recipientName;

    @Column(length = 20)
    private String recipientPhone;

    private String recipientAddress;

    @Column(length = 20)
    private String zipcode;

    private String note;

    @Builder
    public Delivery(Order order, String carrierName, String carrierContact, String trackingNumber,
            DeliveryStatus deliveryStatus, LocalDate expectedAt, LocalDateTime departedAt,
            LocalDateTime deliveredAt, String destinationName, String recipientName,
            String recipientPhone, String recipientAddress, String zipcode, String note) {
        this.order = order;
        this.carrierName = "tutti_carrier";
        this.carrierContact = "Tel) tutti-tutti";
        this.trackingNumber = trackingNumber;
        this.deliveryStatus = deliveryStatus;
        this.expectedAt = expectedAt;
        this.departedAt = departedAt;
        this.deliveredAt = deliveredAt;
        this.destinationName = destinationName;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.recipientAddress = recipientAddress;
        this.zipcode = zipcode;
        this.note = note;
    }
}
