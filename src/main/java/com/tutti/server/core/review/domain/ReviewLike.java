package com.tutti.server.core.review.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Long memberId;

    @Column(name = "is_liked", nullable = false)
    private boolean isLiked;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ReviewLike(Review review, Long memberId) {
        this.review = review;
        this.memberId = memberId;
        this.isLiked = true;
    }

    public void toggleLike(Long memberIdFromToken) {
        if (!this.memberId.equals(memberIdFromToken)) {
            throw new IllegalArgumentException("잘못된 사용자 요청입니다.");
        }
        this.isLiked = !this.isLiked;
    }

    // 좋아요 상태 확인 기능 추가
    public boolean isLiked() {
        return this.isLiked;
    }


    // 예외 처리, 상태 확인과 토글을 한 메서드에서 처리하도록 추가
    public void likeReview(Long memberIdFromToken) {
        if (!this.memberId.equals(memberIdFromToken)) {
            throw new IllegalArgumentException("잘못된 사용자 요청입니다.");
        }
        this.isLiked = true;
    }

    // 좋아요 상태를 취소하는 메서드
    public void cancelLikeReview(Long memberIdFromToken) {
        if (!this.memberId.equals(memberIdFromToken)) {
            throw new IllegalArgumentException("잘못된 사용자 요청입니다.");
        }
        this.isLiked = false;
    }
}
