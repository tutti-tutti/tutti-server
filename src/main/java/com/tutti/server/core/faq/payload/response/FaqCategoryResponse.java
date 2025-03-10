package com.tutti.server.core.faq.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FaqCategoryResponse {

    @Schema(description = "카테고리 ID", example = "5")
    private Long id;

    @Schema(description = "메인 카테고리명", example = "계정 관리")
    private String mainCategory;

    @Schema(description = "서브 카테고리명", example = "챗봇 지혜 VIP")
    private String subCategory;
}
