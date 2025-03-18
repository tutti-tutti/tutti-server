package com.tutti.server.core.product.application;

import com.tutti.server.core.product.payload.response.ProductResponse;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductService {

    public List<ProductResponse> getAllProductsByCategory(@PathVariable Long categoryId);

    public List<ProductResponse> getAllProductsByCreated();
}
