package com.tutti.server.core.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@Tag(name = "최근 본 상품 API", description = "최근 본 상품 목록 조회 및 삭제")
@SecurityRequirement(name = "Bearer Authentication")
public interface ViewedProductApiSpec {

    @Operation(summary = "최근 본 상품 조회")
    ResponseEntity<Map<String, Object>> getViewedProducts(String authHeader, int page, int size);


    @Operation(summary = "최근 본 상품 삭제")
    ResponseEntity<Map<String, Object>> deleteViewedProduct(Long productId,
            String authHeader);
}
