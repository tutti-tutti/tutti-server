package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewDetailService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewDetailResponse getReviewDetail(long reviewId) {
        Review review = reviewRepository.findOne(reviewId);
        return ReviewDetailResponse.from(review);
    }
}
