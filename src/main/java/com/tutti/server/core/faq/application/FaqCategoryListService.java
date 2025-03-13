package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaqCategoryListService {

    private final FaqCategoryRepository faqCategoryRepository;

    public FaqCategoryListService(FaqCategoryRepository faqCategoryRepository) {
        this.faqCategoryRepository = faqCategoryRepository;
    }

    @Transactional(readOnly = true)
    public List<String> getCategories() {
        return faqCategoryRepository.findDistinctMainCategories();
    }
}
