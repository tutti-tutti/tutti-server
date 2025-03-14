package com.tutti.server.core.product.api;

import com.tutti.server.core.product.payload.ProductApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "products", description = "상품 API")
public interface ProductApiSpec {

  @Operation(summary = "JIHYE 상품 api 호출")
  public List<ProductApiResponse> getProductApi();

//  @Operation(summary = "상품 조회")
//  public List<ProductResponse> getProducts();
}
