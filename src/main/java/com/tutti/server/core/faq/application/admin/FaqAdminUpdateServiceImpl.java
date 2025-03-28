package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.domain.FaqCategory;
import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqUpdateRequest;
import com.tutti.server.core.faq.payload.response.FaqUpdateResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqAdminUpdateServiceImpl implements FaqAdminUpdateService {

    private final FaqRepository faqRepository;
    private final FaqCategoryRepository faqCategoryRepository;

    @Override
    @Transactional
    public FaqUpdateResponse updateFaq(Long faqId, FaqUpdateRequest faqUpdateRequest,
            Long memberId) {

        // 관리자 검증: memberId가 1번인 경우만 관리자 권한을 가진다고 가정.
        if (!memberId.equals(1L)) {
            throw new DomainException(ExceptionType.FAQ_ADMIN_ONLY);
        }
        
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new DomainException(ExceptionType.RESOURCE_NOT_FOUND));

        FaqCategory newCategory = faqCategoryRepository.findById(faqUpdateRequest.categoryId())
                .orElseThrow(() -> new DomainException(ExceptionType.RESOURCE_NOT_FOUND));
        faq.updateCategory(newCategory);

        faq.updateFaq(faqUpdateRequest.question(), faqUpdateRequest.answer(),
                faqUpdateRequest.isView());

        return new FaqUpdateResponse(
                faq.getId(),
                faq.getQuestion(),
                faq.getAnswer(),
                faq.isView(),
                faq.getCategoryName()
        );
    }

}
