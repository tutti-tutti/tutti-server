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
        String query = request.query();
        String category = request.category();
        String subcategory = request.subcategory();

        boolean hasQuery = query != null && !query.trim().isEmpty();
        boolean hasCategory = category != null && !category.trim().isEmpty();
        boolean hasSubcategory = subcategory != null && !subcategory.trim().isEmpty();

        Page<Faq> faqs;

        if (hasQuery) {
            faqs = faqRepository.findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
                query.trim(), pageRequest);

        } else if (hasCategory && hasSubcategory) {
            faqs = faqRepository.findByFaqCategoryMainCategoryAndFaqCategorySubCategoryAndDeleteStatusFalseAndIsViewTrue(
                category.trim(), subcategory.trim(), pageRequest);

            if (faqs.isEmpty()) {
                throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
            }

        } else if (hasCategory) {
            faqs = faqRepository.findByFaqCategoryMainCategoryAndDeleteStatusFalseAndIsViewTrue(
                category.trim(), pageRequest);

            if (faqs.isEmpty()) {
                throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
            }

        } else if (hasSubcategory) {
            faqs = faqRepository.findByFaqCategorySubCategoryAndDeleteStatusFalseAndIsViewTrue(
                subcategory.trim(), pageRequest);

            if (faqs.isEmpty()) {
                throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
            }

        } else {
            faqs = faqRepository.findByDeleteStatusFalseAndIsViewTrue(pageRequest);

            if (faqs.isEmpty()) {
                throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
            }
        }

        return faqs;
    }
}
