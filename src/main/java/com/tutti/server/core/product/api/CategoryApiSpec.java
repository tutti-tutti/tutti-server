package com.tutti.server.core.product.api;

import com.tutti.server.core.product.payload.response.CategoryResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Category", description = "카테고리 API")
public interface CategoryApiSpec {

    @Operation(summary = "카테고리 목록 조회")
    public List<CategoryResponse> getAllCategory();

    @Operation(summary = "상품 전체 조회 (카테고리 선택)")
    public List<ProductResponse> getAllProductsByCategory(
            @Parameter(description = "조회할 상품의 카테고리 id", example = "4") long categoryId);


}
