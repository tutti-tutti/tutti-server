package com.tutti.server.core.review.application;

import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.SentimentPositiveAvgResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SentimentPositiveServiceImpl implements SentimentPositiveService {

    private final ReviewRepository reviewRepository;
    private final ProductItemRepository productItemRepository;

    @Override
    @Transactional(readOnly = true)
    public SentimentPositiveAvgResponse sentimentPositiveAvg(Long productId) {
        Double percent = reviewRepository.getPositiveSentimentRate(productId);
        if (percent == null) {
            percent = 0.0;
        }
        return SentimentPositiveAvgResponse.of(productId, percent);
    }
}
