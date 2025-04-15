package com.tutti.server.core.product.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.product.payload.request.SearchRequest;
import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.product.payload.response.ProductSliceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Products", description = "상품 API")
public interface ProductApiSpec {

    @Operation(summary = "상품 전체 조회 (최신상품순)")
    public List<ProductResponse> getAllProductsByCreated();

    @Operation(summary = "상품 전체 조회 (최신상품순, 페이징)")
    public ProductSliceResponse getAllProductsByCreatedWithPagination(
            @Parameter(description = "커서 ID (마지막으로 받은 상품의 ID) / 첫페이지 로딩시 비워두면 됩니다", required = false) Long cursorId,
            @Parameter(description = "페이지당 가져올 상품 개수", required = false) int size);

    @Operation(summary = "상품 상세 조회")
    public ProductItemResponse getProductItemsWithOptions(
            @Parameter(description = "조회할 상품 상세 id", example = "3") long productId,
            @Parameter(hidden = true) CustomUserDetails userDetails);

    @Operation(summary = "상품 검색")
    public ProductSliceResponse getAllSearchedProducts(SearchRequest searchRequest);
}
