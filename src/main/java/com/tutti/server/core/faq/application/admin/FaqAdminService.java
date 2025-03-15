package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;

public interface FaqAdminService {

    FaqCreateResponse createFaq(FaqCreateRequest faqCreateRequest);


}
