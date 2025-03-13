package com.tutti.server.core.product.application;

import com.tutti.server.core.product.infrastructure.ProductFeignClient;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import com.tutti.server.core.product.payload.ProductApiResponse;
import com.tutti.server.core.product.payload.ProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductFeignClient productFeignClient;

  @Override
  public List<ProductApiResponse> fetchAllProducts(Integer limit) {
    return productFeignClient.getAllProducts(limit);
  }

  @Override
  public List<ProductApiResponse> getAllProducts() {
    return List.of();
  }

  @Override
  public List<ProductResponse> getProducts() {
    return List.of();
  }

  @Override
  public List<ProductResponse> getProductsByCategory(String category) {
    return List.of();
  }


}
