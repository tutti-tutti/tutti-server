package com.tutti.server.core.member.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="member_agreement_mappings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "terms_conditions_id"}))
public class MemberAgreementMapping extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terms_conditions_id", nullable = false)
    private TermsConditions termsConditions;

    @Column(nullable = false)
    private boolean isApproved;

    @Builder
    public MemberAgreementMapping(Member member, TermsConditions termsConditions, boolean isApproved) {
        this.member = member;
        this.termsConditions = termsConditions;
        this.isApproved = isApproved;
    }

    // :white_check_mark: 동의 상태 변경 메서드
    public void agree() {
        this.isApproved = true;
    }

    public void disagree() {
        this.isApproved = false;
    }
}
