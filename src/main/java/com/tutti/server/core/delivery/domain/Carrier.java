package com.tutti.server.core.delivery.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "carriers")
public class Carrier extends BaseEntity {

    @Column(length = 50)
    private String carrierName;

    @Column(length = 20)
    private String carrierContact;

    @Builder
    public Carrier() {
        this.carrierName = "tutti_carrier";
        this.carrierContact = "Tel) tutti-tutti";
    }
}
