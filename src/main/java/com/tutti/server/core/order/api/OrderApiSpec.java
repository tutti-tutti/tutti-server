package com.tutti.server.core.order.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.order.payload.request.OrderPageRequest;
import com.tutti.server.core.order.payload.response.CursorBasedOrdersResponse;
import com.tutti.server.core.order.payload.response.OrderDetailResponse;
import com.tutti.server.core.order.payload.response.OrderSheetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;

@Tag(name = "Orders", description = "주문 관련 API")
public interface OrderApiSpec {

    @Operation(summary = "주문서 작성 페이지")
    OrderSheetResponse getOrderPage(OrderPageRequest request);

    @Operation(summary = "주문 내역 전체 조회")
    CursorBasedOrdersResponse getOrders(
            CustomUserDetails user,
            @Parameter(description = """
                    페이징을 위한 커서(기준) 1: createdAt, 이 시간보다 이전에 생성된 주문만 조회됩니다.
                    *주의: Sting 타입 'z'는 LocalDateTime 으로 변환될 수 없습니다!
                    """,
                    example = """
                            2025-04-10T07:10:17.823Z (X)
                            2025-04-10T07:10:17.823  (O)
                            2025-04-10T07:10:17      (O)
                            """
            )
            LocalDateTime cursorCreatedAt,
            @Parameter(description = "페이징을 위한 커서(기준) 2: id, cursorCreatedAt가 같을 경우 이 ID 보다 작은 주문만 조회됩니다.", example = "105189")
            Long cursorId,
            @Parameter(description = "한 페이지에 가져올 데이터(주문)의 개수", example = "(default)10")
            int size);

    @Operation(summary = "주문 내역 상세 조회")
    OrderDetailResponse getOrderDetail(
            @Parameter(description = "조회할 주문 id", example = "1")
            Long orderId,
            CustomUserDetails user);

    @Operation(summary = "주문 내역 삭제")
    void deleteOrder(
            @Parameter(description = "삭제할 주문 id", example = "1")
            Long orderId,
            CustomUserDetails user);
}
