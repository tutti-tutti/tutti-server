package com.tutti.server.core.faq.payload.response;

import com.tutti.server.core.faq.domain.Faq;
import io.swagger.v3.oas.annotations.media.Schema;

public record FaqResponse(
    @Schema(description = "FAQ ID", example = "1")
    Long id,

    @Schema(description = "FAQ 카테고리명", example = "계정 관리")
    String categoryName,

    @Schema(description = "질문", example = "AliExpress VIP 멤버십 서비스를 종료할 수 있나요?")
    String question,

    @Schema(description = "답변", example = "VIP 멤버십 서비스는 계정 설정에서 종료할 수 있습니다.")
    String answer,

    @Schema(description = "조회수", example = "125")
    long viewCnt
) {

    public static FaqResponse fromEntity(Faq faq) {
        return new FaqResponse(
            faq.getId(),
            faq.getCategoryName(),
            faq.getQuestion(),
            faq.getAnswer(),
            faq.getViewCnt()
        );
    }
}
