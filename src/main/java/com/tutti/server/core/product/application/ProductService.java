package com.tutti.server.core.product.application;

import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.product.payload.response.ProductSliceResponse;
import com.tutti.server.core.sku.domain.Sku;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProductsByCategory(Long categoryId);

    List<ProductResponse> getAllProductsByCreated();

    ProductSliceResponse getAllProductsByCreated(Long cursorId, int size);

    ProductSliceResponse getAllProductsBySearchWord(Long cursorId, int size,
            String searchWord);

    ProductItemResponse getProductItemsWithOptions(Long productId);

    List<ProductItem> getProductItemWithOptions(Long productId);

    List<Sku> getSkuListByProductItems(List<ProductItem> productItems);

    void likeProduct(Long productId, Long memeberId);

    void unlikeProduct(Long productId, Long memeberId);

    boolean isProductLiked(Long productId, Long memeberId);

    List<ProductResponse> getProductsByLikes(int size);

    int calculateMatchScore(Long memberId, Long productId);

    List<ProductResponse> recommendProductsForMember(Long memberId, int size);
}

