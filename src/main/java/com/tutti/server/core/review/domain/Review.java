package com.tutti.server.core.review.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    // 리뷰 조회 성능을 고려하여, productId와 memberId는 연관관계를 맺지 않고 단순한 ID 값만 저장
    // 필요할 경우 개별적으로 조회하여 사용 (예: productRepository.findById(productId))
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    // TO-DO: 주문 테이블이 나오면 수정
    // 주문한 사용자만 리뷰를 작성할 수 있도록 검증하기 위해 OrderItem과 연관관계를 설정
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_item_id", nullable = false)
//    private OrderItem orderItem;

    // 1~5점 사이의 평점을 저장
    @Column(nullable = false)
    private Integer rating;

    // 리뷰 내용 (최대 500자)
    @Column(length = 500)
    private String content;

    // 최대 4개의 리뷰 이미지 URL 저장
    @Column(name = "review_image_url", length = 255)
    private String reviewImageUrl;

    @Column(name = "review_image_url2", length = 255)
    private String reviewImageUrl2;

    @Column(name = "review_image_url3", length = 255)
    private String reviewImageUrl3;

    @Column(name = "review_image_url4", length = 255)
    private String reviewImageUrl4;

    // 좋아요 수 (기본값 0)
    @Column(name = "like_count", nullable = false)
    private Long likeCount = 0L;
}