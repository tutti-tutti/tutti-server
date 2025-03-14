package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.ReviewDeleteRequest;
import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewCreateService reviewCreateService;
    private final ReviewListService reviewListService;
    private final ReviewDeleteService reviewDeleteService;

    public ReviewCreateResponse createReview(ReviewCreateRequest request) {
        return reviewCreateService.createReview(request);
    }

    public ReviewListResponse getReviews(ReviewListRequest request) {
        return reviewListService.getReviews(request);
    }

    public ReviewDeleteResponse deleteReview(ReviewDeleteRequest request) {
        return reviewDeleteService.deleteReview(request);
    }
}
