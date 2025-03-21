package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.payload.response.FaqCategoryResponse;
import java.util.List;

public interface FaqCategoryListService {

    List<FaqCategoryResponse> getCategoryList();
}
