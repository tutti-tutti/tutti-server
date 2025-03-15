package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;

public interface FaqAdminCreateService {

    /**
     * FAQ 생성
     *
     * @param faqCreateRequest FAQ 생성 요청 데이터
     * @return FAQ 생성 응답 (FAQ ID 및 메시지)
     */
    FaqCreateResponse createFaq(FaqCreateRequest faqCreateRequest);
}
