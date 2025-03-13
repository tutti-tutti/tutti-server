package com.tutti.server.core.product.application;

import com.tutti.server.core.product.payload.ProductApiResponse;
import java.util.List;

public interface ProductService {

  public List<ProductApiResponse> getAllProducts();
}
