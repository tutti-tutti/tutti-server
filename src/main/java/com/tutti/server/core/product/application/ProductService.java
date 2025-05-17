package com.tutti.server.core.product.application;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.product.payload.response.ProductSliceResponse;
import com.tutti.server.core.sku.domain.Sku;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    Product getProductWithTagsById(Long productId);

    Map<Long, Integer> getMemberTagScoreMap(Long memberId);

    Map<Long, Integer> getMemberCategoryScoreMap(Long memberId);

    Set<Long> extractProductTagIds(Product product);

    Map<Long, Long> extractProductTagFrequencies(Product product);

    int calculateTagScore(Set<Long> productTagIds, Map<Long, Integer> memberTagScoreMap,
            Map<Long, Long> productTagFrequencies);

    int calculateCategoryScore(Long productCategoryId,
            Map<Long, Integer> memberCategoryScoreMap);

    Long findTopLevelCategoryId(Long productId);

    ProductResponse convertToProductResponse(Product product);
}

