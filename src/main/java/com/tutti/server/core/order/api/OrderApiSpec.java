package com.tutti.server.core.order.api;

import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.order.payload.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "주문 API")
public interface OrderApiSpec {

    @Operation(summary = "주문 생성")
    void createOrder(OrderCreateRequest request);

    @Operation(summary = "주문 내역 전체 조회")
    List<OrderResponse> getOrders(Long memberId);
}
