package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
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
    @Override
    public boolean deleteReview(Long reviewId, String userEmail) {
        Review review = reviewRepository.findOne(reviewId);

        if (review == null) {
            throw new DomainException(ExceptionType.PRODUCT_REVIEW_NOT_FOUND);
        }

        if (!review.getAuthorEmail().equals(userEmail)) {
            throw new DomainException(ExceptionType.UNAUTHORIZED_ERROR);
        }

        reviewRepository.delete(review);
        return true;
    }
}
