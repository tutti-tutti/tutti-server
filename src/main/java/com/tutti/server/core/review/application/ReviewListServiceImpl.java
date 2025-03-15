package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewLikeRepository;
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
    private final ReviewLikeRepository reviewLikeRepository;

    @Override
    public ReviewListResponse getReviews(ReviewListRequest request) {
        Pageable pageable = PageRequest.of(0, request.size(), getSort(request.sort()));
        Page<Review> reviewsPage = reviewRepository.findByProductId(request.productId(), pageable);

        List<ReviewResponse> reviewResponses = reviewsPage.stream()
            .map(this::convertToReviewResponse)
            .collect(Collectors.toList());

        String nextCursor = null;
        if (reviewsPage.hasNext()) {
            nextCursor = reviewsPage.getContent().get(reviewsPage.getContent().size() - 1).getId()
                .toString();
        }
        return new ReviewListResponse(reviewResponses, nextCursor);
    }

    private Sort getSort(String sort) {
        switch (sort) {
            case "rating_desc":
                return Sort.by(Sort.Order.desc("rating"));
            case "like_desc":
                return Sort.by(Sort.Order.desc("likeCount"));
            case "created_at_desc":
            default:
                return Sort.by(Sort.Order.desc("createdAt"));
        }
    }

    private ReviewResponse convertToReviewResponse(Review review) {
        long likeCount = reviewLikeRepository.countByReview(review);
        return new ReviewResponse(
            review.getId(),
            review.getContent(),
            review.getRating(),
            likeCount,
            review.getCreatedAt()
        );
    }
}
