package com.tutti.server.core.product.api;

import com.tutti.server.core.product.application.ProductService;
import com.tutti.server.core.product.payload.response.ProductResponse;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryApi implements CategoryApiSpec {

    private final ProductService productService;

    @Override
    @GetMapping("/{categoryId}/products")
    public List<ProductResponse> getAllProductsByCategory(
            @Parameter(description = "조회할 상품의 카테고리 id", example = "4") @PathVariable(name = "categoryId") long categoryId) {
        return productService.getAllProductsByCategory(categoryId);
    }
}
