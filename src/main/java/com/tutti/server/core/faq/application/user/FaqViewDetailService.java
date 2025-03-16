package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.payload.response.FaqResponse;

public interface FaqViewDetailService {

    FaqResponse findFaqById(Long faqId);

}
