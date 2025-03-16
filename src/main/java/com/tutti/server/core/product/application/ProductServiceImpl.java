package com.tutti.server.core.product.application;

import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.store.infrastructure.StoreRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductItemRepository productItemRepository;

  private final StoreRepository storeRepository;

  @Override
  public List<ProductResponse> getProducts() {
    return List.of();
  }

  @Override
  public List<ProductResponse> getProductsByCategory(String category) {
    return List.of();
  }
}
