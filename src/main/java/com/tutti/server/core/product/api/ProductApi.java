package com.tutti.server.core.product.api;

import com.tutti.server.core.product.application.ProductService;
import com.tutti.server.core.product.payload.ProductApiResponse;
import com.tutti.server.core.product.payload.ProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductApi implements ProductApiSpec {

  private final ProductService productService;

  // api 받아오는 메서드
  @Override
  @GetMapping
  public List<ProductApiResponse> getProductApi() {
    // api 쿼리 파라미터를 인자로 줄것
    return productService.getAllProducts();
  }

  @Override
  @GetMapping
  public List<ProductResponse> getProducts() {
    return List.of();
  }
}
