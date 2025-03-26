package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.payload.response.ProductResponse;
import java.util.List;

public interface ViewedProductServiceSpec {

    void saveViewedProduct(Member member, Product product);

    List<ProductResponse> getViewedProductResponses(Member member, int page, int size);

    void deleteViewedProduct(Member member, Long productId);
}
