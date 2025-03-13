package com.tutti.server.core.product.infrastructure;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tutti.server.core.product.payload.ProductApiResponse;
/**
* 외부 API 호출을 담당하는 FeignClient
*/

@FeignClient(name = "productClient", url = "${api.product.url}")
public interface ProductFeignClient {

    /**
     * Get all products from the external service
     * @return List of productApiResponse
     */
    @GetMapping("/products")
    List<ProductApiResponse> getAllProducts(@RequestParam(value = "_limit", required = false) Integer limit);
    
    /**
     * Get a product by its ID
     * @param id the product ID
     * @return Product data as Map
     */
    // @GetMapping("/products/{id}")
    // Map<String, Object> getProductById(@PathVariable("id") Long id);

}