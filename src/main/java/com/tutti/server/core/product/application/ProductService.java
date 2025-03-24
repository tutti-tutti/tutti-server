package com.tutti.server.core.product.application;

import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import java.util.List;

public interface ProductService {

    public List<ProductResponse> getAllProductsByCategory(Long categoryId);

    public List<ProductResponse> getAllProductsByCreated();

    public List<ProductItemResponse> getDetailProductItem(Long productId);
}
