package com.tutti.server.core.store.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Column(length = 50)
    private String name;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 50)
    private String password;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StoreStatus storeStatus;

    @Column
    private LocalDateTime latestLoginDate;

    @Builder
    public Store(String name, String email, String password, String phone, StoreStatus storeStatus,
            LocalDateTime latestLoginDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.storeStatus = storeStatus;
        this.latestLoginDate = latestLoginDate;
    }
}
