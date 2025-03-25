package com.tutti.server.core.review.payload.response;

import lombok.Builder;

@Builder
public record ReviewCountPerStarResponse(
        Long totalCount,
        RatingResponse reviewRatings
) {

}

