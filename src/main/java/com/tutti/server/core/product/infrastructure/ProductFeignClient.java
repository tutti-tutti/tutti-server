package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.payload.ProductApiResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 외부 API 호출을 담당하는 FeignClient
 */

@FeignClient(name = "productFeignClient", url = "http://1.230.77.225:4796")
public interface ProductFeignClient {

  // api url 노출 안되게
  @GetMapping("/products?_limit=100")
  List<ProductApiResponse> getProducts();
}