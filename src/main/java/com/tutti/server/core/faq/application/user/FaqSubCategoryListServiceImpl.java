package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.domain.FaqMainCategory;
import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqSubCategoryListServiceImpl implements FaqSubCategoryListService {

    private final FaqCategoryRepository faqCategoryRepository;

    @Transactional(readOnly = true)
    public List<String> getSubCategories(FaqMainCategory category) {
        List<String> subcategories = faqCategoryRepository.findDistinctSubCategoriesByMainCategory(
            category.getDisplayName());
        if (subcategories.isEmpty()) {
            throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
        }
        return subcategories;
    }
}
