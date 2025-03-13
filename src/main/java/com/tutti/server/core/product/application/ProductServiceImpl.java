package com.tutti.server.core.product.application;

import java.util.List;

import com.tutti.server.core.product.infrastructure.ProductFeignClient;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import com.tutti.server.core.product.payload.ProductApiResponse;
import com.tutti.server.core.product.payload.ProductResponse;

public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductFeignClient productFeignClient;

  public ProductServiceImpl(ProductRepository productRepository, ProductFeignClient productFeignClient){
    this.productRepository = productRepository;
    this.productFeignClient = productFeignClient;
  }

  @Override
  public List<ProductApiResponse> fetchAllProducts(Integer limit){
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
