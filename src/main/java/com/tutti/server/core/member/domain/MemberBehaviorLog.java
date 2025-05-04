package com.tutti.server.core.member.domain;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_behavior_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBehaviorLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "behavior_type", nullable = false)
    private BehaviorType behaviorType;

    @Builder
    public MemberBehaviorLog(Member member, Product product, BehaviorType behaviorType) {
        this.member = member;
        this.product = product;
        this.behaviorType = behaviorType;
    }
}

