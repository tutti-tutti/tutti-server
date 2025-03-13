package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.payload.response.FaqResponse;

public interface FaqViewDetailService {

    FaqResponse findFaqById(Long faqId);

}
