package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.ViewedProduct;
import com.tutti.server.core.member.infrastructure.ViewedProductRepository;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ViewedProductServiceImpl implements ViewedProductServiceSpec {

    private final ViewedProductRepository viewedProductRepository;
    private final ProductItemRepository productItemRepository;

    @Override
    public void saveViewedProduct(Member member, Product product) {
        Optional<ViewedProduct> optional = viewedProductRepository
                .findByMemberAndProductAndDeleteStatusFalse(member, product);

        if (optional.isPresent()) {
            optional.get().updateViewedAt(LocalDateTime.now());
        } else {
            ViewedProduct newView = ViewedProduct.builder()
                    .member(member)
                    .product(product)
                    .build();
            viewedProductRepository.save(newView);
        }

        Pageable pageable = PageRequest.of(0, 51);
        List<ViewedProduct> recent = viewedProductRepository
                .findByMemberAndDeleteStatusFalseOrderByViewedAtDesc(member, pageable)
                .getContent();

        if (recent.size() > 50) {
            recent.subList(50, recent.size()).forEach(ViewedProduct::markAsDeleted);
        }
    }

    @Override
    public List<ProductResponse> getViewedProductResponses(Member member, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return viewedProductRepository
                .findByMemberAndDeleteStatusFalseOrderByViewedAtDesc(member, pageable)
                .stream()
                .map(vp -> {
                    Product product = vp.getProduct();
                    ProductItem item = productItemRepository
                            .findFirstByProductIdOrderBySellingPriceAsc(product.getId())
                            .orElseThrow(() -> new DomainException(
                                    ExceptionType.PRODUCT_ITEM_NOT_FOUND));
                    return ProductResponse.fromEntity(product, item, product.getStoreId());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteViewedProduct(Member member, Long productId) {
        ViewedProduct vp = viewedProductRepository
                .findByMemberAndProductIdAndDeleteStatusFalse(member, productId)
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_ITEM_NOT_FOUND));
        vp.markAsDeleted();
    }
}
