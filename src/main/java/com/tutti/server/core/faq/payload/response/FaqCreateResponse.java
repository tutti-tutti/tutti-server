package com.tutti.server.core.faq.payload.response;

import com.tutti.server.core.faq.domain.Faq;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 생성 응답")
public record FaqCreateResponse(
    Long id,
    String question,
    String answer,
    boolean isView,
    long viewCnt,
    long positive,
    long negative) {

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
