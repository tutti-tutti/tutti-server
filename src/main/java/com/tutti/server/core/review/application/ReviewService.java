package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewCreateService reviewCreateService;

    // 리뷰 생성
    public ReviewCreateResponse createReview(ReviewCreateRequest request) {
        return reviewCreateService.createReview(request);
    }
}
