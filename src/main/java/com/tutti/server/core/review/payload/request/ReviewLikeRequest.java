package com.tutti.server.core.review.payload.request;

public record ReviewLikeRequest(
    Long reviewId,
    Long memberId
) {

}

