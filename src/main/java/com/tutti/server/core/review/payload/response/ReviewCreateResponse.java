package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 생성 응답")
public record ReviewCreateResponse(
    String message // 성공 메시지
) {

}

