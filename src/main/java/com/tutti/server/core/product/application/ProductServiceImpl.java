package com.tutti.server.core.product.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import com.tutti.server.core.product.payload.response.ProductResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductResponse> getProducts() {
        // 모든 상품 리스트 조회
        List<Product> allProducts = productRepository.findAll();

        // ProductResponse 리스트로 변환
        return allProducts.stream()
                .map(product -> {
                    // 해당 상품의 ProductItem 중 가장 낮은 판매가격을 가진 항목 찾기
                    ProductItem lowestPriceItem = productItemRepository.findByProductIdOrderBySellingPriceAsc(
                                    product.getId())
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException(
                                    "Product with id " + product.getId() + " has no valid product items"));

                    return ProductResponse.fromEntity(
                            product,
                            lowestPriceItem,
                            product.getStoreId()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByCategory(String category) {
        return List.of();
    }
}
