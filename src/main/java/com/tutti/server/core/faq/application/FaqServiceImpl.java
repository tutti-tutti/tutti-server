package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FaqServiceImpl implements FaqService {

    private final FaqCategoryListServiceImpl faqCategoryListServiceImpl;
    private final FaqListViewServiceImpl faqListViewServiceImpl;
    private final FaqSearchListServiceImpl faqSearchListServiceImpl;
    private final FaqTopViewedListServiceImpl faqTopViewedListServiceImpl;
    private final FaqViewDetailServiceImpl faqViewDetailServiceImpl;

    public FaqServiceImpl(FaqCategoryListServiceImpl faqCategoryListServiceImpl,
        FaqListViewServiceImpl faqListViewServiceImpl,
        FaqSearchListServiceImpl faqSearchListServiceImpl,
        FaqTopViewedListServiceImpl faqTopViewedListServiceImpl,
        FaqViewDetailServiceImpl faqViewDetailServiceImpl) {
        this.faqCategoryListServiceImpl = faqCategoryListServiceImpl;
        this.faqListViewServiceImpl = faqListViewServiceImpl;
        this.faqSearchListServiceImpl = faqSearchListServiceImpl;
        this.faqTopViewedListServiceImpl = faqTopViewedListServiceImpl;
        this.faqViewDetailServiceImpl = faqViewDetailServiceImpl;
    }

    public List<String> getCategories() {
        return faqCategoryListServiceImpl.getCategories();
    }

    public FaqListResponse getFaqs(FaqListRequest request) {
        return faqListViewServiceImpl.getFaqs(request);
    }

    public FaqListResponse searchFaqs(FaqListRequest request) {
        return faqSearchListServiceImpl.searchFaqs(request);
    }

    public List<FaqResponse> getTopFaqs(int limit) {
        return faqTopViewedListServiceImpl.getTopFaqs(limit);
    }

    public FaqResponse getFaqById(Long faqId) {
        return faqViewDetailServiceImpl.findFaqById(faqId);
    }

}
