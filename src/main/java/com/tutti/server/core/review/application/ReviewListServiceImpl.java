package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewListServiceImpl implements ReviewListService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public ReviewListResponse getReviews(ReviewListRequest request) {
        return getReviewsWithPagination(
            request.productId(),
            Optional.ofNullable(request.size()).orElse(20),
            request.sort(),
            request.nextCursor()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewListResponse getReviewsWithPagination(Long productId, Integer size, String sort,
        String nextCursor) {
        Long cursorId = (nextCursor != null && !nextCursor.isEmpty()) ? Long.parseLong(nextCursor)
            : Long.MAX_VALUE;

        Pageable pageable = PageRequest.of(0, size + 1, getSort(sort));
        List<Review> reviews = reviewRepository.findReviewsByProductIdAndCursor(productId, cursorId,
            pageable);

        String nextCursorValue =
            (reviews.size() > size) ? String.valueOf(reviews.get(size).getId()) : null;

        List<ReviewResponse> reviewResponses = reviews.stream()
            .limit(size)
            .map(ReviewResponse::from)
            .collect(Collectors.toList());

        return new ReviewListResponse(reviewResponses, nextCursorValue);
    }

    private Sort getSort(String sort) {
        return switch (sort.toLowerCase()) {
            case "rating_desc" -> Sort.by(Sort.Order.desc("rating"));
            case "like_desc" -> Sort.by(Sort.Order.desc("likeCount"));
            default -> Sort.by(Sort.Order.desc("createdAt")); // 기본값: 최신순
        };
    }
}
