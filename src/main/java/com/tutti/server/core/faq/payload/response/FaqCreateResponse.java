package com.tutti.server.core.faq.payload.response;

import com.tutti.server.core.faq.domain.Faq;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 생성 응답")
public record FaqCreateResponse(
    @Schema(description = "FAQ ID", example = "1")
    Long id,

    @Schema(description = "질문", example = "반품 신청은 어떻게 하나요?")
    String question,

    @Schema(description = "답변", example = "마이페이지에서 반품 신청을 할 수 있습니다.")
    String answer,

    @Schema(description = "공개 여부", example = "true")
    boolean isView,

    @Schema(description = "조회 수", example = "10")
    long viewCnt,

    @Schema(description = "긍정적인 평가 수", example = "5")
    long positive,

    @Schema(description = "부정적인 평가 수", example = "2")
    long negative
) {

    public FaqCreateResponse(Faq faq) {
        this(
            faq.getId(),
            faq.getQuestion(),
            faq.getAnswer(),
            faq.isView(),
            faq.getViewCnt(),
            faq.getPositive(),
            faq.getNegative()
        );
    }
}


