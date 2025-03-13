package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import com.tutti.server.core.faq.payload.response.FaqSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaqListViewService {

    private final FaqRepository faqRepository;

    public FaqListViewService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Transactional(readOnly = true)
    public FaqListResponse getFaqs(FaqListRequest request) {
        PageRequest pageRequest = PageRequest.of(request.page() - 1, request.size());
        Page<FaqResponse> faqResponses = findFaqs(request, pageRequest);

        return new FaqListResponse(
            (int) faqResponses.getTotalElements(),
            request.page(),
            request.size(),
            new FaqSearchResponse(faqResponses.getContent())
        );
    }

    private Page<FaqResponse> findFaqs(FaqListRequest request, PageRequest pageRequest) {
        Page<Faq> faqs;

        if (request.query() != null && !request.query().isEmpty()) {
            faqs = faqRepository.findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
                request.query(), pageRequest);
        } else if (request.category() != null && request.subcategory() != null) {
            faqs = faqRepository.findByFaqCategory_MainCategoryAndFaqCategory_SubCategoryAndDeleteStatusFalseAndIsViewTrue(
                request.category(), request.subcategory(), pageRequest);
        } else {
            faqs = faqRepository.findByDeleteStatusFalseAndIsViewTrue(pageRequest);
        }

        return faqs.map(FaqResponse::fromEntity);
    }
}
