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

    private final FaqCategoryListServiceImpl faqCategoryListServiceImpl;
    private final FaqListViewServiceImpl faqListViewServiceImpl;
    private final FaqSearchListServiceImpl faqSearchListServiceImpl;
    private final FaqTopViewedListServiceImpl faqTopViewedListServiceImpl;
    private final FaqViewDetailServiceImpl faqViewDetailServiceImpl;
    private final FaqFeedbackServiceImpl faqFeedbackServiceImpl;

    public List<String> getCategories() {
        try {
            return faqCategoryListServiceImpl.getCategories();
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
        }
    }

    public FaqListResponse getFaqs(FaqListRequest request) {
        try {
            return faqListViewServiceImpl.getFaqs(request);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    public FaqListResponse searchFaqs(FaqSearchRequest request) {
        try {
            return faqSearchListServiceImpl.searchFaqs(request);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    public List<FaqResponse> getTopFaqs(int limit) {
        try {
            return faqTopViewedListServiceImpl.getTopFaqs(limit);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    public FaqResponse getFaqById(Long faqId) {
        try {
            return faqViewDetailServiceImpl.findFaqById(faqId);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    public void faqFeedback(Long faqId, FaqFeedbackRequest feedback) {
        try {
            faqFeedbackServiceImpl.updateFaqFeedback(faqId, feedback);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_FEEDBACK_FAILED);
        }
    }
}
