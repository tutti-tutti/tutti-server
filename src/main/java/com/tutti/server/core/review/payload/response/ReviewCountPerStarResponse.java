package com.tutti.server.core.review.payload.response;

import lombok.Builder;

@Builder
public record ReviewCountPerStarResponse(
        Long totalCount,
        RatingResponse reviewRatings
) {

    public static ReviewCountPerStarResponse of(Long totalCount, RatingResponse reviewRatings) {
        return ReviewCountPerStarResponse.builder()
                .totalCount(totalCount)
                .reviewRatings(reviewRatings)
                .build();
    }
}

