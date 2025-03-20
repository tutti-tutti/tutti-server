package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewCreateService reviewCreateService;
    private final ReviewListService reviewListService;
    private final ReviewDetailService reviewDetailService;
    private final ReviewMyListService reviewMyListService;

    public ReviewCreateResponse createReview(ReviewCreateRequest request) {
        return reviewCreateService.createReview(request);
    }

    public ReviewListResponse getReviews(Long productId, Long cursor, int size, String sort) {
        return reviewListService.getReviewsByProductId(productId, cursor, size, sort);
    }

    public ReviewDetailResponse getReviewDetail(long reviewId) {
        return reviewDetailService.getReviewDetail(reviewId);
    }

    public ReviewMyListResponse getMyReviewList(String email, Long cursor, int size) {
        return reviewMyListService.getMyList(email, cursor, size);
    }
}
