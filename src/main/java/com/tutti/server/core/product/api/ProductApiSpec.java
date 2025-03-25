package com.tutti.server.core.product.api;

import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Products", description = "상품 API")
public interface ProductApiSpec {

    @Operation(summary = "상품 전체 조회 (최신상품순)")
    public List<ProductResponse> getAllProductsByCreated();

    @Operation(summary = "상품 상세 조회")
    public ProductItemResponse getProductItemsWithOptions(
            @Parameter(description = "조회할 상품 상세 id", example = "78") long productId);

}
