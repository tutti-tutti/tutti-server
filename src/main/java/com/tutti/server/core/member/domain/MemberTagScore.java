package com.tutti.server.core.member.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import com.tutti.server.core.tag.domain.Tag;
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
@Table(name = "member_tag_score")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTagScore extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @Column(nullable = false)
    private int score;

    @Builder
    public MemberTagScore(Member member, Tag tag, int score) {
        this.member = member;
        this.tag = tag;
        this.score = score;
    }

    public void addScore(int point) {
        this.score += point;
    }
}
