package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;

public interface FaqFeedbackService {

    void updateFaqFeedback(Long id, FaqFeedbackRequest faqFeedbackRequest);
}
