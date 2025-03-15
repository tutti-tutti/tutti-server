package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.domain.FaqCategory;
import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqAdminCreateServiceImpl implements FaqAdminCreateService {

    private final FaqRepository faqRepository;
    private final FaqCategoryRepository faqCategoryRepository;  // FaqCategoryRepository 주입

    @Override
    public FaqCreateResponse createFaq(FaqCreateRequest faqCreateRequest) {
        
        FaqCategory faqCategory = faqCategoryRepository.findOne(faqCreateRequest.categoryId());

        Faq faq = Faq.builder()
            .faqCategory(faqCategory)
            .question(faqCreateRequest.question())
            .answer(faqCreateRequest.answer())
            .isView(faqCreateRequest.isView())
            .build();

        faqRepository.save(faq);

        return new FaqCreateResponse(faq);
    }
}
