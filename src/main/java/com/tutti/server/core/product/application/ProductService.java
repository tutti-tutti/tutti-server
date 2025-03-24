package com.tutti.server.core.product.application;

import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.payload.response.ProductResponse;
import java.util.List;

public interface ProductService {

    public List<ProductResponse> getAllProductsByCategory(Long categoryId);

    public List<ProductResponse> getAllProductsByCreated();

    public ProductItem getProductItemById(Long productItemId);
}
