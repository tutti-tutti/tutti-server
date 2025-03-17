package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 삭제 요청")
public record ReviewDeleteRequest(

    @Schema(description = "리뷰 ID", example = "1")
    long reviewId
) {

}
