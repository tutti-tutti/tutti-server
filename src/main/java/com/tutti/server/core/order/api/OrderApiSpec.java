package com.tutti.server.core.order.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.order.payload.request.OrderPageRequest;
import com.tutti.server.core.order.payload.response.OrderDetailResponse;
import com.tutti.server.core.order.payload.response.OrderPageResponse;
import com.tutti.server.core.order.payload.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;

@Tag(name = "Orders", description = "주문 관련 API")
public interface OrderApiSpec {

    @Operation(summary = "주문서 작성 페이지")
    OrderPageResponse getOrderPage(OrderPageRequest request);

    @Operation(summary = "주문 내역 전체 조회")
    Page<OrderResponse> getOrders(
            CustomUserDetails user,
            @Parameter(description = "조회할 페이지 번호", example = "0") int page,
            @Parameter(description = "한 페이지에 가져올 데이터(주문)의 개수", example = "10") int size,
            @Parameter(description = "정렬 기준이 되는 필드명", example = "createdAt") String sort,
            @Parameter(description = "정렬 방향(ASC/DESC)", example = "DESC") String direction);

    @Operation(summary = "주문 내역 상세 조회")
    OrderDetailResponse getOrderDetail(
            @Parameter(description = "조회할 주문 id", example = "1") Long orderId,
            CustomUserDetails user);

    @Operation(summary = "주문 내역 삭제")
    void deleteOrder(@Parameter(description = "삭제할 주문 id", example = "1") Long orderId,
            CustomUserDetails user);
}
