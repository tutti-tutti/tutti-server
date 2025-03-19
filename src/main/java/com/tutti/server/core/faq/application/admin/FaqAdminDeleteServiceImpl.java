package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.infrastructure.FaqRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqAdminDeleteServiceImpl implements FaqAdminDeleteService {

    private final FaqRepository faqRepository;

    @Override
    @Transactional
    public void deleteFaq(Long faqId) {
        faqRepository.deleteById(faqId);
    }
}

