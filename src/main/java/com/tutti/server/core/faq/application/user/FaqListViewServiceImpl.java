package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import com.tutti.server.core.faq.payload.response.FaqSearchResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqListViewServiceImpl implements FaqListViewService {

    private final FaqRepository faqRepository;

    @Transactional(readOnly = true)
    public FaqListResponse getFaqs(FaqListRequest request) {
        int page = Math.max(request.page() - 1, 0);
        int size = Math.max(request.size(), 1);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FaqResponse> faqResponses = findFaqs(request, pageRequest);

        return new FaqListResponse(
            (int) faqResponses.getTotalElements(),
            request.page(),
            request.size(),
            new FaqSearchResponse(faqResponses.getContent())
        );
    }

    private Page<FaqResponse> findFaqs(FaqListRequest request, PageRequest pageRequest) {
        Page<Faq> faqs = findFaqsByCriteria(request, pageRequest);
        return faqs.map(FaqResponse::fromEntity);
    }

    private Page<Faq> findFaqsByCriteria(FaqListRequest request, PageRequest pageRequest) {
        if (request.query() != null && !request.query().isEmpty()) {
            return faqRepository.findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
                request.query(), pageRequest);
        } else if (request.category() != null && request.subcategory() != null) {
            return faqRepository.findByFaqCategory_MainCategoryAndFaqCategory_SubCategoryAndDeleteStatusFalseAndIsViewTrue(
                request.category(), request.subcategory(), pageRequest);
        } else {
            Page<Faq> faqs = faqRepository.findByDeleteStatusFalseAndIsViewTrue(pageRequest);
            if (faqs.getTotalElements() == 0) {
                throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
            }
            return faqs;
        }
    }
}
