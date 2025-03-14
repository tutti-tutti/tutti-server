package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqCategoryListServiceImpl faqCategoryListServiceImpl;
    private final FaqListViewServiceImpl faqListViewServiceImpl;
    private final FaqSearchListServiceImpl faqSearchListServiceImpl;
    private final FaqTopViewedListServiceImpl faqTopViewedListServiceImpl;
    private final FaqViewDetailServiceImpl faqViewDetailServiceImpl;

    public List<String> getCategories() {
        return faqCategoryListServiceImpl.getCategories();
    }

    public FaqListResponse getFaqs(FaqListRequest request) {
        return faqListViewServiceImpl.getFaqs(request);
    }

    public FaqListResponse searchFaqs(FaqSearchRequest request) {
        return faqSearchListServiceImpl.searchFaqs(request);
    }

    public List<FaqResponse> getTopFaqs(int limit) {
        return faqTopViewedListServiceImpl.getTopFaqs(limit);
    }

    public FaqResponse getFaqById(Long faqId) {
        return faqViewDetailServiceImpl.findFaqById(faqId);
    }

}
