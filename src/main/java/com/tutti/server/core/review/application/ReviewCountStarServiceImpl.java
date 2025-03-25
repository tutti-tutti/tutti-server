package com.tutti.server.core.review.application;

import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.RatingResponse;
import com.tutti.server.core.review.payload.response.ReviewCountPerStarResponse;
import com.tutti.server.core.review.utils.RatingResponseFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCountStarServiceImpl implements ReviewCountStarService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewCountPerStarResponse reviewCountPerStar(Long productId) {
        List<Object[]> starCounts = reviewRepository.countStarGroupBy(productId); // 여기 런타임 오류 위험!

        RatingResponse stars = RatingResponseFactory.fromStarCounts(starCounts);
        long totalCount = RatingResponseFactory.calculateTotalCount(stars);

        return ReviewCountPerStarResponse.of(totalCount, stars);
    }
}
