package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCountPerStarResponse;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import com.tutti.server.core.review.payload.response.ReviewLikeResponse;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import com.tutti.server.core.review.payload.response.ReviewRatingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewCreateService reviewCreateService;
    private final ReviewListService reviewListService;
    private final ReviewDetailService reviewDetailService;
    private final ReviewMyListService reviewMyListService;
    private final ReviewDeleteService reviewDeleteService;
    private final ReviewLikeService reviewLikeService;
    private final ReviewRatingService reviewRatingService;
    private final ReviewCountStarService reviewCountStarService;

    public ReviewCreateResponse createReview(ReviewCreateRequest request, Long memberId) {
        return reviewCreateService.createReview(request, memberId);
    }

    public ReviewListResponse getReviews(Long productId, Long cursor, int size, String sort) {
        return reviewListService.getReviewsByProductId(productId, cursor, size, sort);
    }

    public ReviewDetailResponse getReviewDetail(long reviewId, Long memberId) {
        return reviewDetailService.getReviewDetail(reviewId, memberId);
    }

    public ReviewMyListResponse getMyReviewList(String email, Long cursor, int size) {
        return reviewMyListService.getMyList(email, cursor, size);
    }

    public ReviewDeleteResponse deleteReview(Long reviewId) {
        return reviewDeleteService.deleteMyReview(reviewId);
    }

    public ReviewLikeResponse likeReview(Long reviewId, Long memberId) {
        return reviewLikeService.likeReview(reviewId, memberId);
    }

    public ReviewRatingResponse reviewRating(Long productId) {
        return reviewRatingService.ratingAverage(productId);
    }

    public ReviewCountPerStarResponse reviewCountPerStar(Long productId) {
        return reviewCountStarService.reviewCountPerStar(productId);
    }
}
