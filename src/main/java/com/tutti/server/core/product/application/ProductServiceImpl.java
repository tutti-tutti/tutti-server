package com.tutti.server.core.product.application;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;

    @Override
    public List<ProductResponse> getAllProductsByCreated() {
        // 생성일자 기준 내림차순으로 모든 상품 조회
        List<Product> products = productRepository.findAllByOrderByCreatedAtDesc();

        // ProductResponse 리스트로 변환
        return products.stream()
            .map(product -> {
                // 해당 상품의 ProductItem 중 가장 낮은 판매가격을 가진 항목 찾기
                ProductItem lowestPriceItem = productItemRepository
                    .findFirstByProductIdOrderBySellingPriceAsc(product.getId())
                    .orElseThrow(() -> new DomainException(
                        ExceptionType.PRODUCT_ITEM_NOT_FOUND));

                return ProductResponse.fromEntity(
                    product,
                    lowestPriceItem,
                    product.getStoreId()
                );
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findProductsByCategoryId(categoryId);

        return products.stream()
            .map(product -> {
                ProductItem lowestPriceItem = productItemRepository
                    .findFirstByProductIdOrderBySellingPriceAsc(product.getId())
                    .orElseThrow(() -> new DomainException(
                        ExceptionType.PRODUCT_ITEM_NOT_FOUND));

                return ProductResponse.fromEntity(
                    product,
                    lowestPriceItem,
                    product.getStoreId()
                );
            })
            .collect(Collectors.toList());
    }

    @Override
    public ProductItem getProductItemById(Long productItemId) {
        return productItemRepository.findById(productItemId)
            .orElseThrow(() -> new DomainException(
                ExceptionType.PRODUCT_ITEM_NOT_FOUND));
    }
}
