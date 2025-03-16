package com.tutti.server.core.product.application;

import com.tutti.server.core.product.payload.response.ProductResponse;
import java.util.List;

public interface ProductService {

  public List<ProductResponse> getProducts();

  public List<ProductResponse> getProductsByCategory(String category);

}
