package com.tutti.server.core.product.application;

import java.util.List;

import com.tutti.server.core.product.payload.ProductApiResponse;
import com.tutti.server.core.product.payload.ProductResponse;

public interface ProductService {

  public List<ProductApiResponse> fetchAllProducts(Integer limit);

  public List<ProductApiResponse> getAllProducts();

  public List<ProductResponse> getProducts();

  public List<ProductResponse> getProductsByCategory(String category);
}
