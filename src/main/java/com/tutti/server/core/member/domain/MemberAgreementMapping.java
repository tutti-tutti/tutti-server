package com.tutti.server.core.member.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="member_agreement_mappings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "type"}))
public class MemberAgreementMapping extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", nullable = false)
    private TermsConditions termsConditions;

    @Column(nullable = false)
    private boolean isApproved =false;

    @Builder
    public MemberAgreementMapping(Member member, TermsConditions termsConditions, boolean isApproved) {
        this.member = member;
        this.termsConditions = termsConditions;
        this.isApproved = isApproved;
    }

    // :white_check_mark: 동의 상태 변경 메서드
    public void approve() {
        this.isApproved = true;
    }

    public void disapprove() {
        this.isApproved = false;
    }
}
