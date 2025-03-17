package com.tutti.server.core.product.api;

import com.tutti.server.core.product.application.ProductService;
import com.tutti.server.core.product.payload.response.ProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductApi implements ProductApiSpec {

    private final ProductService productService;

    /*
        @Override
        @GetMapping("/findAll")
        public List<Product> getAllProducts() {
            return productService.getAllProducts();
        }
    */
    @Override
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getProducts();
    }
}
