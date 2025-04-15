package com.tutti.server.core.product.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.product.payload.request.SearchRequest;
import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.product.payload.response.ProductSliceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(summary = "상품 추천 조회 (좋아요순)")
    public List<ProductResponse> getProductsByLikes(
            @Parameter(description = "받아올 상품 개수", required = false) int size);

    @Operation(summary = "상품 검색")
    public ProductSliceResponse getAllSearchedProducts(
            @Parameter(description = "keyword- 검색어\ncursorId- 다음페이지 요청을 위한 productId\nsize- 한페이지당 요청 상품 수", schema = @Schema(implementation = SearchRequest.class,
                    description = "검색어, 커서 ID, 페이지 크기를 포함하는 검색 요청 객체"))
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "검색어 요청",
                    content = @Content(schema = @Schema(implementation = SearchRequest.class),
                            examples = {
                                    @ExampleObject(name = "검색어 \"케이스\"로 첫페이지를 요청하는 방식입니다.",
                                            value = "{\"keyword\": \"케이스\", \"cursorId\": null, \"size\": 10}",
                                            summary = "첫 페이지 검색"),
                                    @ExampleObject(name = "cursorId 값이 존재하는 중간페이지를 요청하는 방식입니다.\n응답 마지막 productId가 다음요청을 위한 cursorId 입니다!",
                                            value = "{\"keyword\": \"케이스\", \"cursorId\": 6, \"size\": 10}",
                                            summary = "중간 페이지 검색"),
                                    @ExampleObject(name = "검색어 \"케이스\"로 마지막 페이지를 요청하는 방식입니다.",
                                            value = "{\"keyword\": \"케이스\", \"cursorId\": 17, \"size\": 10}",
                                            summary = "마지막 페이지 검색")
                            }
                    ))
            SearchRequest searchRequest);
}
