package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
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
        Faq faq = faqRepository.findOne(faqId);

        if (request.feedback()) {
            faq.incrementPositive();
        } else {
            faq.incrementNegative();
        }

        faqRepository.save(faq);
    }
}
