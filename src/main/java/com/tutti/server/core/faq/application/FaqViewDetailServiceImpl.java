package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.response.FaqResponse;
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
        
        Faq faq = faqRepository.findOne(faqId);
        faqIncrementViewCountServiceImpl.incrementViewCount(faqId);
        return FaqResponse.fromEntity(faq);
    }

}
