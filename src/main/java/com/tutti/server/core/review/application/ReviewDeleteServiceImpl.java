package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewDeleteServiceImpl implements ReviewDeleteService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewDeleteResponse deleteMyReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(
                () -> new DomainException(ExceptionType.PRODUCT_REVIEW_NOT_FOUND, reviewId));

        reviewRepository.delete(review);

        return new ReviewDeleteResponse(reviewId, "리뷰가 삭제되었습니다.");
    }
}
