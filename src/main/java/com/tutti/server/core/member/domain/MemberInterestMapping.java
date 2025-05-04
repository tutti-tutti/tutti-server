package com.tutti.server.core.member.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_interest_mappings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInterestMapping extends BaseEntity {

    // 회원 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 관심사 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id", nullable = false)
    private Interest interest;

    @Column(length = 1)
    private String gender;

    @Column(length = 1)
    private String age;

    @Builder
    public MemberInterestMapping(Member member, Interest interest,
            String gender, String age) {
        this.member = member;
        this.interest = interest;
        this.gender = gender;
        this.age = age;
    }
}
