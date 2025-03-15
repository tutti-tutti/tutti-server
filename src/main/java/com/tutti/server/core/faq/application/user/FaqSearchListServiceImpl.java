package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
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
public class FaqSearchListServiceImpl implements FaqSearchListService {

    private final FaqRepository faqRepository;

    @Transactional(readOnly = true)
    public FaqListResponse searchFaqs(FaqSearchRequest request) {
        FaqListRequest faqListRequest = convertToFaqListRequest(request);

        PageRequest pageRequest = PageRequest.of(faqListRequest.page() - 1, faqListRequest.size());

        Page<FaqResponse> faqResponses = findFaqs(faqListRequest, pageRequest);

        return new FaqListResponse(
            (int) faqResponses.getTotalElements(),
            faqListRequest.page(),
            faqListRequest.size(),
            new FaqSearchResponse(faqResponses.getContent())
        );
    }

    private FaqListRequest convertToFaqListRequest(FaqSearchRequest request) {
        return new FaqListRequest(
            request.query(),
            null,
            null,
            request.page(),
            request.size()
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

        if (faqs.isEmpty()) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }

        return faqs.map(FaqResponse::fromEntity);
    }
}
