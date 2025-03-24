package com.tutti.server.core.product.application;

import com.tutti.server.core.product.payload.response.ProductResponse;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProductsByCategory(Long categoryId);

    List<ProductResponse> getAllProductsByCreated();
}
