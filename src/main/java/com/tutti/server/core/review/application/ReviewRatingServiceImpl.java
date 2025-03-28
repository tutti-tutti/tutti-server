package com.tutti.server.core.review.application;

import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.ReviewRatingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewRatingServiceImpl implements ReviewRatingService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public ReviewRatingResponse ratingAverage(Long productId) {
        Double response = reviewRepository.findAverageRatingByProductId(productId);
        String formatted = String.format("%.1f", response != null ? response : 0.0);

        return new ReviewRatingResponse(productId, formatted);
    }
}
