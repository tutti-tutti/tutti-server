package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqFeedbackServiceImpl implements FaqFeedbackService {

    private final FaqRepository faqRepository;

    @Transactional
    @Override
    public void updateFaqFeedback(Long faqId, FaqFeedbackRequest request) {
        Faq faq = faqRepository.findById(faqId)
            .orElseThrow(() -> new EntityNotFoundException("FAQ not found"));

        if (request.feedback()) {
            faq.incrementPositive();
        } else {
            faq.incrementNegative();
        }

        faqRepository.save(faq);
    }
}
