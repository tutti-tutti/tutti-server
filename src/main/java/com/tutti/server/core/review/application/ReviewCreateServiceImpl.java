package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCreateServiceImpl implements ReviewCreateService {

    private final ReviewRepository reviewRepository;

    public ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest) {

        String reviewImagesString = String.join(",", reviewCreateRequest.reviewImages());

        Review review = Review.builder()
            .productId(reviewCreateRequest.productId())
            .nickname(reviewCreateRequest.nickname())
            .rating(reviewCreateRequest.rating())
            .content(reviewCreateRequest.content())
            .reviewImageUrls(reviewImagesString)
            .build();

        reviewRepository.save(review);

        return new ReviewCreateResponse("리뷰가 등록되었습니다.");
    }

    private Long getMemberIdFromUsername(String username) {
        if ("testUser".equals(username)) {
            return 1L;
        }
        return 999L;
    }
}
