package com.tutti.server.core.review.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "review_likes")
@Getter
@NoArgsConstructor
public class ReviewLike extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "member_id", nullable = false, updatable = false)
    private long memberId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ReviewLike(Review review, Long memberId) {
        this.review = review;
        this.memberId = memberId;
        review.increaseLikeCount(); // 좋아요 추가 시 증가
    }

    @PreRemove
    public void preRemove() {
        review.decreaseLikeCount(); // 삭제 시 감소
    }
}
