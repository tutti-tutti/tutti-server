package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "FAQ 검색 요청")
public record FaqSearchRequest(
    @Schema(description = "검색 키워드", example = "쿠폰")
    String query,

    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    @Schema(description = "페이지 번호 (기본값: 1)", example = "1")
    int page,

    @Min(value = 1, message = "페이지당 데이터 개수는 1 이상이어야 합니다.")
    @Schema(description = "페이지당 데이터 개수 (기본값: 20)", example = "20")
    int size
) {

}
