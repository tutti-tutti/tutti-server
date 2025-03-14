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
        long cursorId = 0L;
        if (request.nextCursor() != null) {
            cursorId = Long.parseLong(request.nextCursor());
        }

        Pageable pageable = PageRequest.of(0, 20, getSort(request.sort()));

        Page<Review> reviewsPage;
        // 커서 값이 있을 경우, 해당 ID 이후의 리뷰를 가져옴
        reviewsPage = reviewRepository.findByProductIdAndIdGreaterThan(
            request.productId(), cursorId, pageable);

        List<ReviewResponse> reviewResponses = reviewsPage.stream()
            .map(this::convertToReviewResponse)
            .collect(Collectors.toList());

        String nextCursor = null;
        if (reviewsPage.hasNext()) {
            nextCursor = reviewsPage.getContent().get(reviewsPage.getContent().size() - 1).getId()
                .toString();
        }

        return new ReviewListResponse(reviewResponses, nextCursor); // List<ReviewResponse> 반환
    }

    private Sort getSort(String sort) {
        return switch (sort) {
            case "latest" -> Sort.by(Sort.Order.asc("creat"));
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
}
