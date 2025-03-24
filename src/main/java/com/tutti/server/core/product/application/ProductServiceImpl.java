package com.tutti.server.core.product.application;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import com.tutti.server.core.product.infrastructure.SkuRepository;
import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.stock.domain.Sku;
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
    private final SkuRepository skuRepository;

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
    public List<ProductItemResponse> getDetailProductItem(Long productId) {
        // 상품 조회
        Product product = productRepository.findOne(productId);

        // 해당 상품의 삭제되지 않은 ProductItem들 조회
        List<ProductItem> productItems = productItemRepository.findActiveItemsByProductId(
                productId);

        if (productItems.isEmpty()) {
            throw new DomainException(ExceptionType.PRODUCT_ITEM_NOT_FOUND);
        }

        // 해당 ProductItem들의 Sku 정보 조회
        List<Sku> skus = skuRepository.findByProductItems(productItems);

        // ProductItemResponse 생성
        ProductItemResponse response = ProductItemResponse.fromEntity(
                product,
                productItems,
                skus,
                product.getStoreId()
        );

        return List.of(response);
    }

    // firstOptionName, firstOtionValue, secondOptionName, secondOptionValue 등을 productOptionResponse로 바꿔주는 서비스
    // 파라미터로 productId 주기 
    // 리턴값은 ProductOptionResponse


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
}
