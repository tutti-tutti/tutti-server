package com.tutti.server.core.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
//@EnableFeignClients(basePackages = "com.tutti.server.core.product.infrastructure")
public class FeignConfig {
  // Configuration can be extended with custom decoders, error handling, etc.
}
