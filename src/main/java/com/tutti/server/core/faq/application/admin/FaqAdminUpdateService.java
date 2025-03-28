package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.payload.request.FaqUpdateRequest;
import com.tutti.server.core.faq.payload.response.FaqUpdateResponse;

public interface FaqAdminUpdateService {

    FaqUpdateResponse updateFaq(Long faqId, FaqUpdateRequest faqUpdateRequest, Long memberId);
}
