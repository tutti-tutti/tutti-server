package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqMainCategoryListServiceImpl implements FaqMainCategoryListService {

    private final FaqCategoryRepository faqCategoryRepository;

    @Transactional(readOnly = true)
    public List<String> getMainCategories() {
        try {
            List<String> categories = faqCategoryRepository.findDistinctMainCategories();
            if (categories.isEmpty()) {
                throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
            }
            return categories;
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
        }
    }
}
