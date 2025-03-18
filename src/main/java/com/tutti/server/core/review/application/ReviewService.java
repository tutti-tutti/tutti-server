package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewCreateService reviewCreateService;
    //    private final ReviewListService reviewListService;
    private final ReviewDetailService reviewDetailService;

    public ReviewCreateResponse createReview(ReviewCreateRequest request) {
        return reviewCreateService.createReview(request);
    }

//    public ReviewListResponse getReviews(ReviewListRequest request) {
//        return reviewListService.getReviews(request);
//    }

    public ReviewDetailResponse getReviewDetail(long reviewId) {
        return reviewDetailService.getReviewDetail(reviewId);
    }
}
