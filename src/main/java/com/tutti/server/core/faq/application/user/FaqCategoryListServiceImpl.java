package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.payload.model.FaqCategoryRow;
import com.tutti.server.core.faq.payload.response.FaqCategoryResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqCategoryListServiceImpl implements FaqCategoryListService {

    private final FaqCategoryRepository faqCategoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FaqCategoryResponse> getCategoryList() {
        List<FaqCategoryRow> categories = faqCategoryRepository.findAllCategories();

        if (categories.isEmpty()) {
            throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
        }

        Map<String, Set<String>> groupedCategories = groupByMainCategory(categories);
        return convertToResponse(groupedCategories);
    }

    private Map<String, Set<String>> groupByMainCategory(List<FaqCategoryRow> categories) {
        return categories.stream()
            .collect(Collectors.groupingBy(
                FaqCategoryRow::mainCategory,
                LinkedHashMap::new,
                Collectors.mapping(
                    FaqCategoryRow::subCategory,
                    Collectors.toCollection(LinkedHashSet::new)
                )
            ));
    }

    private List<FaqCategoryResponse> convertToResponse(
        Map<String, Set<String>> groupedCategories) {
        return groupedCategories.entrySet().stream()
            .map(entry -> new FaqCategoryResponse(
                entry.getKey(),
                new ArrayList<>(entry.getValue())
            ))
            .toList();
    }
}
