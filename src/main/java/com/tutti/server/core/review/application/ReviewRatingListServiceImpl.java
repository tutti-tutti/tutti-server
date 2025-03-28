package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewLikeRepository;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.RatingReviewCursor;
import com.tutti.server.core.review.payload.response.RatingReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import com.tutti.server.core.review.utils.ReviewLike;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewRatingListServiceImpl implements ReviewRatingListService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    @Override
    public RatingReviewListResponse getRatingReviews(Long productId, RatingReviewCursor cursor,
            int size, Long memberId) {
        int limit = size + 1;

        boolean isEmptyCursor = (cursor == null || cursor.reviewId() == null
                || cursor.rating() == null);

        List<Review> reviews;
        if (!isEmptyCursor) {
            reviews = reviewRepository.findNextReviewsByProductOrderByRatingDescNative(
                    productId,
                    cursor.rating(), cursor.rating(), cursor.reviewId(),
                    limit);
        } else {
            reviews = reviewRepository.findFirstReviewsByProductOrderByRatingDescNative(productId,
                    limit);
        }

        boolean hasNext = reviews.size() > size;
        if (hasNext) {
            reviews = reviews.subList(0, size);
        }

        RatingReviewCursor nextCursor = reviews.stream()
                .reduce((first, second) -> second)
                .map(r -> new RatingReviewCursor(r.getId(), r.getRating()))
                .orElse(null);

        List<ReviewResponse> responses = ReviewLike.toResponseListWithLikes(
                reviews, memberId, reviewLikeRepository
        );

        return new RatingReviewListResponse(responses, nextCursor, hasNext);
    }
}
