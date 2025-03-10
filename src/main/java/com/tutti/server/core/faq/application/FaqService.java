package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class FaqService {

    private final FaqCategoryRepository faqCategoryRepository;

    /**
     * FAQ 카테고리 목록 조회
     */
    @Transactional(readOnly = true)
    public List<String> getCategories() {
        return faqCategoryRepository.findDistinctMainCategories();
    }
}
