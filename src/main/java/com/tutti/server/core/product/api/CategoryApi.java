package com.tutti.server.core.product.api;

import com.tutti.server.core.product.application.ProductService;
import com.tutti.server.core.product.payload.response.ProductResponse;
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
            @PathVariable(name = "categoryId") long categoryId) {
        return productService.getAllProductsByCategory(categoryId);
    }
}
