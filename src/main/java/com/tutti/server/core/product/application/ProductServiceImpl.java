package com.tutti.server.core.product.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductOptionResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.sku.domain.Sku;
import com.tutti.server.core.sku.infrastructure.SkuRepository;
import com.tutti.server.core.store.domain.Store;
import com.tutti.server.core.store.infrastructure.StoreRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final SkuRepository skuRepository;
    private final StoreRepository storeRepository;

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
    public ProductItemResponse getProductItemsWithOptions(Long productId) {
        // 1. 주어진 productId에 대한 모든 ProductItem 목록 가져오기
        List<ProductItem> productItems = getProductItemWithOptions(productId);
        List<ProductOptionResponse> options = productItems.stream()
                .map(ProductOptionResponse::from)
                .toList();

        // 2. 상품 조회
        Product product = productRepository.findOne(productId);

        // 3. 스토어 조회
        Store store = storeRepository.findOne(product.getStoreId().getId());

        // 4. 모든 ProductItem에 대한 SKU 정보 조회
        List<Sku> skuList = getSkuListByProductItems(productItems);

        // SKU 존재 여부 확인 및 최소 재고 SKU 찾기
        Sku minStockSku = null;
        if (skuList.size() == productItems.size()) {
            minStockSku = findSkuWithMinimumStock(skuList);
        }
        // 없으면 예외처리

        // 5. fromEntities 메서드를 사용하여 통합된 응답 생성
        return ProductItemResponse.fromEntity(product, options, store, minStockSku);
    }


    // productId를 받아서 옵션을 매핑하고 productItems 반환하는 메서드
    @Override
    public List<ProductItem> getProductItemWithOptions(Long productId) {
        // 상품 조회
        Product product = productRepository.findOne(productId);

        // 해당 상품의 삭제되지 않은 ProductItem들 조회
        List<ProductItem> productItems = productItemRepository.findActiveItemsByProductId(
                productId);

        if (productItems.isEmpty()) {
            throw new DomainException(ExceptionType.PRODUCT_ITEM_NOT_FOUND);
        }

        return productItems;
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
    public List<Sku> getSkuListByProductItems(List<ProductItem> productItems) {
        // 모든 ProductItem ID를 추출
        List<Long> productItemIds = productItems.stream()
                .map(ProductItem::getId)
                .collect(Collectors.toList());

        // ProductItemIds에 대한 Sku 목록 조회
        return skuRepository.findByProductItemIds(productItemIds);
    }

    public Sku findSkuWithMinimumStock(List<Sku> skus) {
        if (skus.isEmpty()) {
            throw new DomainException(ExceptionType.SKU_NOT_FOUND);
        }
        
        return skus.stream()
                .min((sku1, sku2) -> 
                    Integer.compare(sku1.getStockQuantity(), sku2.getStockQuantity()))
                .get();
    }
}
