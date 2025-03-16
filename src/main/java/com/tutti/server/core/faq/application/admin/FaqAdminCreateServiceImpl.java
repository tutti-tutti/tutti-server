package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.domain.FaqCategory;
import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqAdminCreateServiceImpl implements FaqAdminCreateService {

    private final FaqRepository faqRepository;
    private final FaqCategoryRepository faqCategoryRepository;

    @Override
    public FaqCreateResponse createFaq(FaqCreateRequest faqCreateRequest) {

        FaqCategory faqCategory = faqCategoryRepository.findOne(faqCreateRequest.categoryId());
        if (faqCategory == null) {
            throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND, "지정된 카테고리가 존재하지 않습니다.");
        }

        Faq faq = Faq.builder()
            .faqCategory(faqCategory)
            .question(faqCreateRequest.question())
            .answer(faqCreateRequest.answer())
            .isView(faqCreateRequest.isView())
            .build();

        try {
            faqRepository.save(faq);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_FEEDBACK_FAILED, "FAQ 저장에 실패했습니다.");
        }
        return new FaqCreateResponse(faq);
    }
}
