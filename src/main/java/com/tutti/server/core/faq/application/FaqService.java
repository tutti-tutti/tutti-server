package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FaqService {

    private final FaqCategoryListService faqCategoryListService;
    private final FaqListViewService faqListViewService;
    private final FaqSearchListService faqSearchListService;
    private final FaqTopViewedListService faqTopViewedListService;
    private final FaqViewDetailService faqViewDetailService;

    public FaqService(FaqCategoryListService faqCategoryListService,
        FaqListViewService faqListViewService,
        FaqSearchListService faqSearchListService,
        FaqTopViewedListService faqTopViewedListService,
        FaqViewDetailService faqViewDetailService) {
        this.faqCategoryListService = faqCategoryListService;
        this.faqListViewService = faqListViewService;
        this.faqSearchListService = faqSearchListService;
        this.faqTopViewedListService = faqTopViewedListService;
        this.faqViewDetailService = faqViewDetailService;
    }

    public List<String> getCategories() {
        return faqCategoryListService.getCategories();
    }

    public FaqListResponse getFaqs(FaqListRequest request) {
        return faqListViewService.getFaqs(request);
    }

    public FaqListResponse searchFaqs(FaqListRequest request) {
        return faqSearchListService.searchFaqs(request);
    }

    public List<FaqResponse> getTopFaqs(int limit) {
        return faqTopViewedListService.getTopFaqs(limit);
    }

    public FaqResponse getFaqById(Long faqId) {
        return faqViewDetailService.findFaqById(faqId);
    }

}
