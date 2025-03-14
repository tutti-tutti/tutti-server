package com.tutti.server.core.review.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
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

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "order_item_id", nullable = false)
    private Long orderItemId;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 500)
    private String content;

    // 리뷰 이미지 URL을 저장할 필드 (하드코딩된 이미지를 배열로 저장)
    @Column(name = "review_image_urls", length = 1000)
    private String reviewImageUrls;

    @Column(name = "like_count", nullable = false)
    private long likeCount;

    public static Review createReview(Long productId, Long memberId, Long orderItemId,
        Integer rating, String content, List<String> reviewImages, String nickname) {
        // 리뷰 이미지 URL 배열을 문자열로 합쳐서 저장
        String reviewImagesString = String.join(",", reviewImages);

        return Review.builder()
            .productId(productId)
            .memberId(memberId)
            .nickname(nickname)
            .orderItemId(orderItemId)
            .rating(rating)
            .content(content)
            .reviewImageUrls(reviewImagesString) // 이미지 URL을 문자열로 저장
            .likeCount(0L)
            .build();
    }

    // 이미지를 업데이트하는 메서드들
    public void updateReviewImages(List<String> reviewImages) {
        this.reviewImageUrls = String.join(",", reviewImages);  // 이미지 배열을 문자열로 합쳐서 저장
    }

    // 좋아요 수 증가
    public void increaseLike() {
        this.likeCount += 1;
    }

    // 문자열로 저장된 reviewImageUrls을 다시 List<String> 형태로 변환하는 유틸리티 메서드
    public List<String> getReviewImages() {
        return List.of(this.reviewImageUrls.split(","));
    }
}
