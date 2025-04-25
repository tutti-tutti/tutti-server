package com.tutti.server.core.member.domain;

import com.tutti.server.core.product.domain.ProductCategory;
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
@Table(name = "member_category_score")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCategoryScore extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    @Column
    private int score;

    @Builder
    public MemberCategoryScore(Member member, ProductCategory category, int score) {
        this.member = member;
        this.category = category;
        this.score = score;
    }

    public void addScore(int point) {
        this.score += point;
    }
}
