package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;

public interface FaqListViewService {
    
    FaqListResponse getFaqs(FaqListRequest request);
}
