package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaqViewDetailServiceImpl implements FaqViewDetailService {

    private final FaqRepository faqRepository;
    private final FaqIncrementViewCountServiceImpl faqIncrementViewCountServiceImpl;

    public FaqViewDetailServiceImpl(FaqRepository faqRepository,
        FaqIncrementViewCountServiceImpl faqIncrementViewCountServiceImpl) {
        this.faqRepository = faqRepository;
        this.faqIncrementViewCountServiceImpl = faqIncrementViewCountServiceImpl;
    }

    @Transactional
    public FaqResponse findFaqById(Long faqId) {
        // FAQ 조회
        Faq faq = faqRepository.findByIdAndDeleteStatusFalseAndIsViewTrue(faqId)
            .orElseThrow(() -> new EntityNotFoundException("FAQ를 찾을 수 없습니다. ID: " + faqId));

        // 조회수 증가 처리 (비동기)
        faqIncrementViewCountServiceImpl.incrementViewCount(faqId);

        // FAQ 응답 반환
        return FaqResponse.fromEntity(faq);
    }
}
