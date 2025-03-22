package com.tutti.server.core.order.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.order.payload.request.OrderPageRequest;
import com.tutti.server.core.order.payload.response.OrderDetailResponse;
import com.tutti.server.core.order.payload.response.OrderPageResponse;
import com.tutti.server.core.order.payload.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "주문 API")
public interface OrderApiSpec {

    @Operation(summary = "주문서 작성 페이지에 데이터 전달")
    OrderPageResponse getOrderPage(OrderPageRequest request);

    @Operation(summary = "주문 생성")
    void createOrder(OrderCreateRequest request);

    @Operation(summary = "주문 내역 전체 조회")
    List<OrderResponse> getOrders(Long memberId);

    @Operation(summary = "주문 내역 상세 조회")
    OrderDetailResponse getOrderDetail(CustomUserDetails user,
            @Parameter(description = "조회할 주문 id", example = "1") Long orderId);

    @Operation(summary = "주문 내역 삭제")
    void deleteOrder(CustomUserDetails user,
            @Parameter(description = "삭제할 주문 id", example = "1") Long orderId);
}
