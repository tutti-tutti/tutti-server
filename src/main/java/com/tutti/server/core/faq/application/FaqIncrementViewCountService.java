package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.infrastructure.FaqRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaqIncrementViewCountService {

    private final FaqRepository faqRepository;

    public FaqIncrementViewCountService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Async
    @Transactional
    public void incrementViewCount(Long faqId) {
        faqRepository.incrementViewCount(faqId);
    }
}
