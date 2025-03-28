package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqAdminDeleteServiceImpl implements FaqAdminDeleteService {

    private final FaqRepository faqRepository;

    @Override
    @Transactional
    public void deleteFaq(Long faqId, Long memberId) {

        // 관리자 검증: memberId가 1번인 경우만 관리자 권한을 가진다고 가정.
        if (!memberId.equals(1L)) {
            throw new DomainException(ExceptionType.FAQ_ADMIN_ONLY);
        }

        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new DomainException(ExceptionType.FAQ_NOT_FOUND, faqId));

        faqRepository.delete(faq);
    }
}

