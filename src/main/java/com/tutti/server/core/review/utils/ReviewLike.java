package com.tutti.server.core.review.utils;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewLikeRepository;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import java.util.List;
import java.util.Set;

public class ReviewLike {

    public static List<ReviewResponse> toResponseListWithLikes(
            List<Review> reviews,
            Long memberId,
            ReviewLikeRepository reviewLikeRepository
    ) {
        if (memberId == null) {
            return reviews.stream()
                    .map(ReviewResponse::from)
                    .toList();
        }

        List<Long> reviewIds = reviews.stream().map(Review::getId).toList();
        Set<Long> likedIdSet = Set.copyOf(
                reviewLikeRepository.findLikedReviewIdsByMemberIdAndReviewIds(memberId, reviewIds)
        );

        return reviews.stream()
                .map(review -> ReviewResponse.from(review, likedIdSet.contains(review.getId())))
                .toList();
    }
}

