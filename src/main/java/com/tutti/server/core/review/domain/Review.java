package com.tutti.server.core.review.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 500)
    private String content;

    // 리뷰 이미지 URL을 저장할 필드 (하드코딩된 이미지를 배열로 저장)
    @Column(name = "review_image_urls", length = 1000)
    private String reviewImageUrls;

    @Column
    private LocalDateTime createdAt;

}
