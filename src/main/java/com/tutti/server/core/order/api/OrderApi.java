package com.tutti.server.core.order.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.order.application.OrderService;
import com.tutti.server.core.order.payload.request.OrderSheetRequest;
import com.tutti.server.core.order.payload.response.CursorBasedOrdersResponse;
import com.tutti.server.core.order.payload.response.OrderDetailResponse;
import com.tutti.server.core.order.payload.response.OrderSheetResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderApi implements OrderApiSpec {

    private final OrderService orderService;

    @Override
    @PostMapping("/ordersheet")
    public OrderSheetResponse getOrderSheet(@Valid @RequestBody OrderSheetRequest request) {
        return orderService.getOrderSheet(request);
    }

    @Override
    @GetMapping
    public CursorBasedOrdersResponse getOrders(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(value = "cursorCreatedAt", required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(value = "cursorId", required = false) Long cursorId,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return orderService.getOrders(user.getMemberId(), cursorCreatedAt, cursorId, size);
    }

    @Override
    @GetMapping("/{orderId}")
    public OrderDetailResponse getOrderDetail(@PathVariable("orderId") Long orderId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return orderService.getOrderDetail(orderId, user.getMemberId());
    }

    @Override
    @PatchMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") Long orderId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        orderService.deleteOrder(orderId, user.getMemberId());
    }
}
