package com.tutti.server.core.faq.payload.response;

import com.tutti.server.core.faq.domain.Faq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FaqResponse {

    @Schema(description = "FAQ ID", example = "1")
    private long id;

    @Schema(description = "FAQ 카테고리 정보")
    private FaqCategoryResponse faqCategory;

    @Schema(description = "질문", example = "AliExpress VIP 멤버십 서비스를 종료할 수 있나요?")
    private String question;

    @Schema(description = "답변", example = "VIP 멤버십 서비스는 계정 설정에서 종료할 수 있습니다.")
    private String answer;

    @Schema(description = "조회수", example = "125")
    private long viewCnt;

    public static FaqResponse fromEntity(Faq faq) {
        return FaqResponse.builder()
            .id(faq.getId())
            .question(faq.getQuestion())
            .answer(faq.getAnswer())
            .viewCnt(faq.getViewCnt())
            .build();
    }
}
