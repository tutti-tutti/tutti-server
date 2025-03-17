package com.tutti.server.core.order.api;

import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "주문 API")
public interface OrderApiSpec {

    @Operation(summary = "주문 생성")
    void createOrder(OrderCreateRequest request);
}
