package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.domain.FaqMainCategory;
import java.util.List;

public interface FaqSubCategoryListService {

    List<String> getSubCategories(FaqMainCategory category);
}
