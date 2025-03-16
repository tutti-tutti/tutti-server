package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
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
    @Override
    public void incrementViewCount(Long faqId) {
        Faq faq = faqRepository.findById(faqId)
            .orElseThrow(() -> new DomainException(ExceptionType.FAQ_NOT_FOUND));
        faqRepository.incrementViewCount(faqId);
    }
}
