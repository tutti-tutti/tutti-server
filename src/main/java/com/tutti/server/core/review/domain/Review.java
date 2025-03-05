package com.tutti.server.core.review.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    // 조회 성능 최적화: FK 대신 ID만 저장
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_item_id", nullable = false)
//    private OrderItem orderItem;

    @Column(nullable = false)
    private Integer rating;

    @Lob
    @Column(nullable = false)
    private String content;

    // 개별 이미지 URL 저장 (최대 4개 지원)
    @Column(name = "review_image_url1", length = 255)
    private String reviewImageUrl1;

    @Column(name = "review_image_url2", length = 255)
    private String reviewImageUrl2;

    @Column(name = "review_image_url3", length = 255)
    private String reviewImageUrl3;

    @Column(name = "review_image_url4", length = 255)
    private String reviewImageUrl4;

    @Column(name = "like_count", nullable = false)
    private Long likeCount = 0L;

    public void updateReviewImage1(String reviewImageUrl1) {
        this.reviewImageUrl1 = reviewImageUrl1;
    }

    public void updateReviewImage2(String reviewImageUrl2) {
        this.reviewImageUrl2 = reviewImageUrl2;
    }

    public void updateReviewImage3(String reviewImageUrl3) {
        this.reviewImageUrl3 = reviewImageUrl3;
    }

    public void updateReviewImage4(String reviewImageUrl4) {
        this.reviewImageUrl4 = reviewImageUrl4;
    }

    public void increaseLike() {
        this.likeCount += 1;
    }

}
