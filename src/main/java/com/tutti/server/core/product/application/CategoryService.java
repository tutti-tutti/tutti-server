package com.tutti.server.core.product.application;

import com.tutti.server.core.product.payload.response.CategoryResponse;
import java.util.List;

public interface CategoryService {

    public List<CategoryResponse> getAllCategory();

}
