package com.tutti.server.core.faq.payload.response;

import com.tutti.server.core.faq.domain.FaqCategory;
import io.swagger.v3.oas.annotations.media.Schema;

public record FaqCategoryResponse(
    @Schema(description = "FAQ 카테고리 ID", example = "1")
    Long id,

    @Schema(description = "메인 카테고리", example = "배송 관련")
    String mainCategory,

    @Schema(description = "서브 카테고리", example = "국내 배송")
    String subCategory,

    @Schema(description = "설명", example = "국내 배송 정책 관련 FAQ")
    String description
) {

    public static FaqCategoryResponse fromEntity(FaqCategory faqCategory) {
        return new FaqCategoryResponse(
            faqCategory.getId(),
            faqCategory.getMainCategory(),
            faqCategory.getSubCategory(),
            faqCategory.getDescription()
        );
    }
}
