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

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "order_item_id", nullable = false)
    private Long orderItemId;

    @Column(nullable = false)
    private Integer rating;

    @Lob
    @Column(nullable = false)
    private String content;

    // 최대 4개의 리뷰 이미지를 저장할 수 있도록 설정
    @Column(name = "review_image_url1", length = 255)
    private String reviewImageUrl1;

    @Column(name = "review_image_url2", length = 255)
    private String reviewImageUrl2;

    @Column(name = "review_image_url3", length = 255)
    private String reviewImageUrl3;

    @Column(name = "review_image_url4", length = 255)
    private String reviewImageUrl4;

    @Column(name = "like_count", nullable = false)
    private long likeCount = 0L;

    /**
     * 리뷰 생성 메서드 (사진 개수가 0~4개일 수 있음)
     */
    public static Review createReview(Long productId, Long memberId, Long orderItemId,
        Integer rating, String content, String[] reviewImages) {
        return Review.builder()
            .productId(productId)
            .memberId(memberId)
            .orderItemId(orderItemId)
            .rating(rating)
            .content(content)
            .reviewImageUrl1(reviewImages.length > 0 ? reviewImages[0] : null)
            .reviewImageUrl2(reviewImages.length > 1 ? reviewImages[1] : null)
            .reviewImageUrl3(reviewImages.length > 2 ? reviewImages[2] : null)
            .reviewImageUrl4(reviewImages.length > 3 ? reviewImages[3] : null)
            .likeCount(0L)
            .build();
    }

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
