package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.LikeReviewCursor;
import com.tutti.server.core.review.payload.response.LikeReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewLikeListServiceImpl implements ReviewLikeListService {

    private final ReviewRepository reviewRepository;

    public LikeReviewListResponse getLikeReviews(Long productId, LikeReviewCursor cursor,
            int size) {

        int limit = size + 1;

        boolean isEmptyCursor = (cursor == null || cursor.reviewId() == null
                || cursor.likeCount() == null);

        List<Review> reviews;

        if (!isEmptyCursor) {
            reviews = reviewRepository.findNextReviewsByProductOrderByLikeDescNative(productId,
                    cursor.likeCount(), cursor.likeCount(), cursor.reviewId(), limit);
        } else {
            reviews = reviewRepository.findFirstReviewsByProductOrderByLikeDescNative(productId,
                    limit);
        }

        boolean hasNext = reviews.size() > size;
        if (hasNext) {
            reviews = reviews.subList(0, size);
        }

        LikeReviewCursor nextCursor = reviews.stream()
                .reduce((first, second) -> second)
                .map(r -> new LikeReviewCursor(r.getId(), r.getLikeCount()))
                .orElse(null);

        List<ReviewResponse> responses = reviews.stream()
                .map(ReviewResponse::from)
                .toList();

        return new LikeReviewListResponse(responses, nextCursor, hasNext);
    }
}
