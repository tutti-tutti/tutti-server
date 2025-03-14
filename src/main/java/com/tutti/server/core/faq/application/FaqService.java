package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;

public interface FaqService {

    List<String> getCategories();

    FaqListResponse getFaqs(FaqListRequest request);

    FaqListResponse searchFaqs(FaqSearchRequest request);

    List<FaqResponse> getTopFaqs(int limit);

    FaqResponse getFaqById(Long faqId);
}
