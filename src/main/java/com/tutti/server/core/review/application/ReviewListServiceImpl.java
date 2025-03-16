package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import java.util.List;
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
        int size = (request.size() == null) ? 20 : request.size();
        String sort = (request.sort() == null) ? "created_at_desc" : request.sort();
        return getReviewsWithPagination(request.productId(), size, sort, request.nextCursor());
    }

    @Override
    public ReviewListResponse getInitialReviews(Long productId, Integer size, String sort) {
        return getReviewsWithPagination(productId, size, sort, null);
    }

    @Override
    public ReviewListResponse getReviewsWithPagination(Long productId, Integer size, String sort,
        String nextCursor) {
        Pageable pageable = PageRequest.of(0, size, getSort(sort));
        Page<Review> reviewsPage;

        if (nextCursor != null) {
            long cursor = Long.parseLong(nextCursor);

            if ("rating_desc".equals(sort)) {
                reviewsPage = reviewRepository.findByProductIdAndRatingLessThanEqual(
                    productId, cursor, pageable);
            }
//            else if ("like_desc".equals(sort)) {
//                reviewsPage = reviewRepository.findByProductIdAndLikeCountLessThanEqual(
//                    productId, cursor, pageable);
//            }
            else {
                reviewsPage = reviewRepository.findByProductIdAndIdLessThan(
                    productId, cursor, pageable);
            }
        } else {
            reviewsPage = reviewRepository.findByProductId(productId, pageable);
        }

        List<ReviewResponse> reviewResponses = convertToReviewResponseList(reviewsPage);

        String nextCursorValue = null;
        if (reviewsPage.hasNext()) {
            Review lastReview = reviewsPage.getContent().get(reviewsPage.getContent().size() - 1);

            nextCursorValue = switch (sort) {
                case "rating_desc" -> String.valueOf(lastReview.getRating());
                default -> lastReview.getId().toString();
            };
        }

        return new ReviewListResponse(reviewResponses, nextCursorValue);
    }

    private Sort getSort(String sort) {
        return switch (sort.toLowerCase()) {
            case "rating_desc" -> Sort.by(Sort.Order.desc("rating"), Sort.Order.desc("id"));
            case "like_desc" -> Sort.by(Sort.Order.desc("likeCount"), Sort.Order.desc("id"));
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
            .toList();
    }
}
