package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewDetailService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewDetail(long reviewId) {
        Review review = reviewRepository.findOne(reviewId);
        return ReviewDetailResponse.from(review);
    }
}
