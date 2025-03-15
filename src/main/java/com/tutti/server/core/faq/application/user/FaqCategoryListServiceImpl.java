package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqCategoryListServiceImpl implements FaqCategoryListService {

    private final FaqCategoryRepository faqCategoryRepository;

    @Transactional(readOnly = true)
    public List<String> getCategories() {
        return faqCategoryRepository.findDistinctMainCategories();
    }
}
