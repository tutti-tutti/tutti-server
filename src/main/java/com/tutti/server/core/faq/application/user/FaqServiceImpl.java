package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.domain.FaqMainCategory;
import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqCategoryResponse;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqCategoryListService faqCategoryListService;
    private final FaqMainCategoryListService faqMainCategoryListService;
    private final FaqSubCategoryListService faqSubCategoryListService;
    private final FaqListViewService faqListViewService;
    private final FaqSearchListService faqSearchListService;
    private final FaqTopViewedListService faqTopViewedListService;
    private final FaqViewDetailService faqViewDetailService;
    private final FaqFeedbackService faqFeedbackService;

    public List<FaqCategoryResponse> getCategories() {
        return faqCategoryListService.getCategoryList();
    }

    public List<String> getMainCategories() {
        return faqMainCategoryListService.getMainCategories();
    }

    public List<String> getSubCategories(FaqMainCategory category) {
        return faqSubCategoryListService.getSubCategories(category);
    }

    public FaqListResponse getFaqs(FaqListRequest request) {
        return faqListViewService.getFaqs(request);
    }

    public FaqListResponse searchFaqs(FaqSearchRequest request) {
        return faqSearchListService.searchFaqs(request);
    }

    public List<FaqResponse> getTopFaqs(int limit) {
        return faqTopViewedListService.getTopFaqs(limit);
    }

    public FaqResponse getFaqById(Long faqId) {
        return faqViewDetailService.findFaqById(faqId);
    }

    public void faqFeedback(Long faqId, FaqFeedbackRequest feedback) {
        faqFeedbackService.updateFaqFeedback(faqId, feedback);
    }
}

