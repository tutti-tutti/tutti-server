package com.tutti.server.core.faq.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FaqListResponse {

    @Schema(description = "총 결과 개수", example = "9")
    private int totalResults;

    @Schema(description = "현재 페이지 번호", example = "1")
    private int currentPage;

    @Schema(description = "페이지당 FAQ 개수", example = "10")
    private int size;

    @Schema(description = "FAQ 목록")
    private List<FaqResponse> results;
}
