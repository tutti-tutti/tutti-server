package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.domain.FaqMainCategory;
import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqCategoryResponse;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;

public interface FaqService {

    List<String> getMainCategories();

    List<String> getSubCategories(FaqMainCategory category);

    List<FaqCategoryResponse> getCategories();

    FaqListResponse getFaqs(FaqListRequest request);

    FaqListResponse searchFaqs(FaqSearchRequest request);

    List<FaqResponse> getTopFaqs(int limit);

    FaqResponse getFaqById(Long faqId);

    void faqFeedback(Long faqId, FaqFeedbackRequest feedback);
}
