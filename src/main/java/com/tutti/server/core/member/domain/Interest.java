package com.tutti.server.core.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {

    @Id
    @Column(name = "interest_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "interest_name", length = 50, nullable = false)
    private InterestType interestType;

    @Builder
    public Interest(Long id, InterestType interestType) {
        this.id = id;
        this.interestType = interestType;
    }
}
