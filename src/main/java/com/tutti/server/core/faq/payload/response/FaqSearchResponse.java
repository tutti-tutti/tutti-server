package com.tutti.server.core.faq.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "FAQ 검색 응답")
public record FaqSearchResponse(
    @Schema(description = "검색된 FAQ 목록", example = "[FAQ 객체 배열]")
    List<FaqResponse> faqs
) {

}
