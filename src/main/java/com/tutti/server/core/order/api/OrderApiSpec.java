package com.tutti.server.core.order.api;

import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.order.payload.response.OrderListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "주문 API")
public interface OrderApiSpec {

    @Operation(summary = "주문 생성")
    void createOrder(OrderCreateRequest request);

    @Operation(summary = "주문 전체 조회")
    PagedModel<OrderListResponse> getOrders(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "15") int pageSize);
}
