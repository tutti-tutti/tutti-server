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
import org.springframework.web.bind.annotation.PathVariable;

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

    @Operation(summary = "상품 좋아요")
    void likeProduct(@PathVariable long productId,
            @Parameter(hidden = true) CustomUserDetails userDetails);

    @Operation(summary = "상품 좋아요 취소")
    void unlikeProduct(@PathVariable long productId,
            @Parameter(hidden = true) CustomUserDetails userDetails);

    @Operation(summary = "상품 좋아요 여부 확인")
    boolean isLiked(@PathVariable long productId,
            @Parameter(hidden = true) CustomUserDetails userDetails);
    @Operation(summary = "상품 추천 조회 (좋아요순)")
    public List<ProductResponse> getProductsByLikes(
            @Parameter(description = "받아올 상품 개수", required = false) int size);

    @Operation(summary = "상품 검색", description = "검색어와 무한스크롤을 위한 정보를 요청하여 조회합니다.")
    public ProductSliceResponse getAllSearchedProducts(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Example Value와 Schema를 통해 예시와 각 필드의 값을 설명합니다. cursorId는 데이터베이스의 데이터에 따라 수정될 수 있습니다.",
                    content = @Content(schema = @Schema(implementation = SearchRequest.class),
                            examples = {
                                    @ExampleObject(name = "검색어 \"케이스\"로 첫페이지를 요청하는 방식입니다.",
                                            value = "{\"keyword\": \"케이스\", \"cursorId\": null, \"size\": 10}",
                                            summary = "첫 페이지 검색"),
                                    @ExampleObject(name = "cursorId 값이 존재하는 중간페이지를 요청하는 방식입니다.\n응답 마지막 productId가 다음요청을 위한 cursorId 입니다!",
                                            value = "{\"keyword\": \"케이스\", \"cursorId\": 70, \"size\": 10}",
                                            summary = "중간 페이지 검색"),
                                    @ExampleObject(name = "검색어 \"케이스\"로 마지막 페이지를 요청하는 방식입니다.",
                                            value = "{\"keyword\": \"케이스\", \"cursorId\": 58, \"size\": 10}",
                                            summary = "마지막 페이지 검색")
                            }
                    ))
            SearchRequest searchRequest);
}
