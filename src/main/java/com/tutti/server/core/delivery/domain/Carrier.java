package com.tutti.server.core.delivery.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "carriers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Carrier extends BaseEntity {

    @Column(length = 50)
    private String carrierName;

    @Column(length = 20)
    private String carrierContact;
}
