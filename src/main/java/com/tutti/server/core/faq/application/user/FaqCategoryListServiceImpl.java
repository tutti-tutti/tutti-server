package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.payload.response.FaqCategoryResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqCategoryListServiceImpl implements FaqCategoryListService {

    private final FaqCategoryRepository faqCategoryRepository;

    @Transactional(readOnly = true)
    public List<FaqCategoryResponse> getCategoryList() {

        List<Object[]> result = faqCategoryRepository.findAllMainAndSubCategories();

        if (result.isEmpty()) {
            throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
        }

        Map<String, Set<String>> grouped = new LinkedHashMap<>();
        for (Object[] row : result) {
            String main = (String) row[0];
            String sub = (String) row[1];
            grouped.computeIfAbsent(main, k -> new LinkedHashSet<>()).add(sub);
        }

        return grouped.entrySet().stream()
            .map(
                entry -> new FaqCategoryResponse(entry.getKey(), new ArrayList<>(entry.getValue())))
            .toList();
    }
}
