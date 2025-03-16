package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewListServiceImpl implements ReviewListService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewListResponse getReviews(ReviewListRequest request) {
        return getReviewsWithPagination(
            request.productId(), 20, request.sort(), request.nextCursor());
    }


    @Override
    public ReviewListResponse getReviewsWithPagination(Long productId, Integer size, String sort,
        String nextCursor) {
        long cursorId = (nextCursor != null) ? Long.parseLong(nextCursor) : 0L;
        Pageable pageable = PageRequest.of(0, size, getSort(sort));

        Page<Review> reviewsPage = reviewRepository.findByProductIdAndIdGreaterThan(
            productId, cursorId, pageable);

        List<ReviewResponse> reviewResponses = convertToReviewResponseList(reviewsPage);

        String nextCursorValue = null;
        if (reviewsPage.hasNext()) {
            nextCursorValue = reviewsPage.getContent().get(reviewsPage.getContent().size() - 1)
                .getId().toString();
        }

        return new ReviewListResponse(reviewResponses, nextCursorValue);
    }

    private Sort getSort(String sort) {
        return switch (sort.toLowerCase()) {
            case "latest" -> Sort.by(Sort.Order.desc("latest"));
            case "rating" -> Sort.by(Sort.Order.desc("rating"));
            default -> Sort.by(Sort.Order.desc("createdAt"));
        };
    }

    private ReviewResponse convertToReviewResponse(Review review) {
        return new ReviewResponse(
            review.getId(),
            review.getProductId(),
            review.getNickname(),
            review.getContent(),
            review.getRating(),
            review.getCreatedAt()
        );
    }

    private List<ReviewResponse> convertToReviewResponseList(Page<Review> reviewsPage) {
        return reviewsPage.stream()
            .map(this::convertToReviewResponse)
            .collect(Collectors.toList());
    }
}
