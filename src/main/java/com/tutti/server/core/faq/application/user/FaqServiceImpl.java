package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqCategoryListService faqCategoryListService;
    private final FaqListViewService faqListViewService;
    private final FaqSearchListService faqSearchListService;
    private final FaqTopViewedListService faqTopViewedListService;
    private final FaqViewDetailService faqViewDetailService;
    private final FaqFeedbackService faqFeedbackService;

    public List<String> getCategories() {
        try {
            return faqCategoryListService.getCategories();
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
        }
    }

    public FaqListResponse getFaqs(FaqListRequest request) {
        try {
            return faqListViewService.getFaqs(request);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    public FaqListResponse searchFaqs(FaqSearchRequest request) {
        try {
            return faqSearchListService.searchFaqs(request);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    public List<FaqResponse> getTopFaqs(int limit) {
        try {
            return faqTopViewedListService.getTopFaqs(limit);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    public FaqResponse getFaqById(Long faqId) {
        try {
            return faqViewDetailService.findFaqById(faqId);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    public void faqFeedback(Long faqId, FaqFeedbackRequest feedback) {
        try {
            faqFeedbackService.updateFaqFeedback(faqId, feedback);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_FEEDBACK_FAILED);
        }
    }
}
