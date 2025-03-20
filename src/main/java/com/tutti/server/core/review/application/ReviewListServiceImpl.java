package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewListServiceImpl implements ReviewListService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public ReviewListResponse getReviewsByProductId(Long productId, Long cursor, int size,
        String sort) {
        final PageRequest pageRequest = PageRequest.of(0, size);
        List<Review> reviews = getReviews(productId, cursor, sort, pageRequest);

        Long nextCursor = reviews.stream()
            .reduce((first, second) -> second)
            .map(Review::getId)
            .orElse(null);

        List<ReviewResponse> reviewResponses = reviews.stream()
            .map(ReviewResponse::from)
            .toList();

        return new ReviewListResponse(reviewResponses, nextCursor);
    }

    private List<Review> getReviews(Long productId, Long cursor, String sort,
        PageRequest pageRequest) {
        return switch (sort) {
            case "rating_desc" -> Optional.ofNullable(cursor)
                .map(c -> reviewRepository.findNextReviewsByProductOrderByRatingDesc(productId, c,
                    pageRequest))
                .orElseGet(
                    () -> reviewRepository.findFirstReviewsByProductOrderByRatingDesc(productId,
                        pageRequest));
            case "like_desc" -> Optional.ofNullable(cursor)
                .map(c -> reviewRepository.findNextReviewsByProductOrderByLikeDesc(productId, c,
                    pageRequest))
                .orElseGet(
                    () -> reviewRepository.findFirstReviewsByProductOrderByLikeDesc(productId,
                        pageRequest));
            default -> Optional.ofNullable(cursor)
                .map(c -> reviewRepository.findNextReviewsByProduct(productId, c, pageRequest))
                .orElseGet(
                    () -> reviewRepository.findFirstReviewsByProduct(productId, pageRequest));
        };
    }
}
