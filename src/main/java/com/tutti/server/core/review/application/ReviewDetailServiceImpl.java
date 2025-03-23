package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewLikeRepository;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewDetailServiceImpl implements ReviewDetailService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewDetail(long reviewId, Long memberId) {
        Review review = reviewRepository.findOne(reviewId);

        boolean liked =
            (memberId != null) && reviewLikeRepository.findByReviewAndMemberId(review, memberId)
                .isPresent();
        return ReviewDetailResponse.from(review, liked);
    }
}
