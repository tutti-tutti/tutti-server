package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewLikeRepository;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.LatestReviewCursor;
import com.tutti.server.core.review.payload.response.LatestReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import com.tutti.server.core.review.utils.ReviewLike;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewLatestListServiceImpl implements ReviewLatestListService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    @Transactional(readOnly = true)
    public LatestReviewListResponse getLatestReviews(Long productId, LatestReviewCursor cursor,
            int size, Long memberId) {
        int limit = size + 1;
        boolean isEmptyCursor = (cursor == null || cursor.reviewId() == null);

        List<Review> reviews = !isEmptyCursor
                ? reviewRepository.findNextReviewsByProductNative(productId, cursor.reviewId(),
                limit)
                : reviewRepository.findFirstReviewsByProductNative(productId, limit);

        boolean hasNext = reviews.size() > size;
        if (hasNext) {
            reviews = reviews.subList(0, size);
        }

        LatestReviewCursor nextCursor = reviews.stream()
                .reduce((first, second) -> second)
                .map(r -> new LatestReviewCursor(r.getId()))
                .orElse(null);

        List<ReviewResponse> responses = ReviewLike.toResponseListWithLikes(
                reviews, memberId, reviewLikeRepository
        );

        return new LatestReviewListResponse(responses, nextCursor, hasNext);
    }


}
