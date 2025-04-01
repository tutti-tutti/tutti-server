package com.tutti.server.core.product.application;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.sku.domain.Sku;

public interface ProductService {

    List<ProductResponse> getAllProductsByCategory(Long categoryId);

    List<ProductResponse> getAllProductsByCreated();

    Slice<Product> getAllProductsByCreated(Long cursorId, int size);

    ProductItemResponse getProductItemsWithOptions(Long productId);

    List<ProductItem> getProductItemWithOptions(Long productId);

    List<Sku> getSkuListByProductItems(List<ProductItem> productItems);
}

