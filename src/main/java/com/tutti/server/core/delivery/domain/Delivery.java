package com.tutti.server.core.delivery.domain;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.returns.domain.Returns;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "deliveries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Comment("주문 ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "returns_id")
    @Comment("반품 ID")
    private Returns returns;

    @Column(length = 50)
    @Comment("택배사 이름")
    private String carrierName;

    @Column(length = 20)
    @Comment("택배사 연락처")
    private String carrierContact;

    @Column(length = 50)
    @Comment("송장 번호")
    private String trackingNumber;

    @Column(length = 50)
    @Comment("배송 상태")
    private DeliveryStatus deliveryStatus;

    @Comment("배송 예정일")
    private LocalDate expectedAt;

    @Comment("배송 출발일")
    private LocalDateTime departedAt;

    @Comment("배송 완료일")
    private LocalDateTime deliveredAt;

    @Column(length = 50)
    @Comment("받는 사람")
    private String recipientName;

    @Column(length = 20)
    @Comment("받는 사람 연락처")
    private String recipientPhone;

    @Column(length = 20)
    @Comment("우편번호")
    private String zipcode;

    @Comment("배송 주소")
    private String recipientAddress;

    @Comment("배송 상세 주소")
    private String recipientAddressDetail;

    @Comment("배송 요청 사항")
    private String note;

    @Builder
    public Delivery(Order order, Returns returns, String trackingNumber,
            DeliveryStatus deliveryStatus, LocalDate expectedAt, LocalDateTime departedAt,
            LocalDateTime deliveredAt, String recipientName, String recipientPhone,
            String recipientAddress, String recipientAddressDetail, String zipcode, String note
    ) {
        this.order = order;
        this.returns = returns;
        this.carrierName = "tutti_carrier";
        this.carrierContact = "Tel) tutti-tutti";
        this.trackingNumber = trackingNumber;
        this.deliveryStatus = deliveryStatus;
        this.expectedAt = expectedAt;
        this.departedAt = departedAt;
        this.deliveredAt = deliveredAt;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.recipientAddress = recipientAddress;
        this.recipientAddressDetail = recipientAddressDetail;
        this.zipcode = zipcode;
        this.note = note;
    }
}
