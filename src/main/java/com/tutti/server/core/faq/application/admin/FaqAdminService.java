package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.request.FaqUpdateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import com.tutti.server.core.faq.payload.response.FaqUpdateResponse;

public interface FaqAdminService {

    FaqCreateResponse createFaq(FaqCreateRequest faqCreateRequest);

    FaqUpdateResponse updateFaq(Long FaqId, FaqUpdateRequest faqUpdateRequest);

}
