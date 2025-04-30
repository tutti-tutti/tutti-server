package com.tutti.server.core.product.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.sku.domain.Sku;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProductsByCategory(Long categoryId);

    List<ProductResponse> getAllProductsByCreated();

    ProductItemResponse getProductItemsWithOptions(Long productId);

    List<ProductItem> getProductItemWithOptions(Long productId);

    List<Sku> getSkuListByProductItems(List<ProductItem> productItems);

    void likeProduct(Long productId, Member member);

    void unlikeProduct(Long productId, Member member);

    boolean isProductLiked(Long productId, Member member);
}
