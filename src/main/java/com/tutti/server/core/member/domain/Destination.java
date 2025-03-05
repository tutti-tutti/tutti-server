package com.tutti.server.core.member.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "destinations")
public class Destination extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    @Column(length = 50)
    private String destinationName; // 배송지 이름 (ex. 집, 회사)

    @Column(length =50)
    private String recipientName; //수령인 이름

    @Column(length = 255)
    private String address; //주소

    @Column (length = 255)
    private String addressDetail;

    @Column
    private Integer zipCode;

    @Column(length=50)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean isDefault = false;

    @Builder
    public Destination(Member member, String destinationName, String recipientName, String address, String addressDetail, Integer zipCode, String phoneNumber, boolean isDefault) {
        this.member = member;
        this.destinationName = destinationName;
        this.recipientName = recipientName;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.isDefault = false;
    }

    public void setDefault() {
        this.isDefault = true;
    }
}
