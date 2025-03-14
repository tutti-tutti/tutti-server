package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.infrastructure.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqIncrementViewCountServiceImpl implements FaqIncrementViewCountService {

    private final FaqRepository faqRepository;

    @Async
    @Transactional
    public void incrementViewCount(Long faqId) {
        faqRepository.incrementViewCount(faqId);
    }
}
