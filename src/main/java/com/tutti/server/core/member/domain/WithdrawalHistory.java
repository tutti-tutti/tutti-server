package com.tutti.server.core.member.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "withdrawal_histories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class WithdrawalHistory {

    @Id
    private Long memberId; // PK이면서 FK 역할

    @OneToOne
    @MapsId  // member_id를 Member의 PK와 공유
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(length = 1)
    private String gender; // 'M' 또는 'F'

    @Column
    private int age; //int는 not null 자동 적용

    @Column(nullable = false)
    private LocalDateTime withdrawalAt;

    @Builder
    public WithdrawalHistory(Member member, String reason, String gender, int age, LocalDateTime withdrawalAt) {
        this.member = member;
        this.reason = reason;
        this.gender = gender;
        this.age = age;
        this.withdrawalAt = (withdrawalAt != null) ? withdrawalAt : LocalDateTime.now();
    }
}
