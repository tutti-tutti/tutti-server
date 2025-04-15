package com.tutti.server.core.order.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.order.application.OrderService;
import com.tutti.server.core.order.payload.request.OrderPageRequest;
import com.tutti.server.core.order.payload.response.OrderDetailResponse;
import com.tutti.server.core.order.payload.response.OrderPageResponse;
import com.tutti.server.core.order.payload.response.OrderResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @PostMapping("/checkout")
    public OrderPageResponse getOrderPage(@Valid @RequestBody OrderPageRequest request) {
        return orderService.getOrderPage(request);
    }

    @Override
    @GetMapping
    public Page<OrderResponse> getOrders(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction) {

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.valueOf(direction),
                sort
        );

        return orderService.getOrders(user.getMemberId(), pageRequest);
    }

    @Override
    @GetMapping("/{orderId}")
    public OrderDetailResponse getOrderDetail(@PathVariable("orderId") Long orderId,
            @AuthenticationPrincipal CustomUserDetails user) {
        return orderService.getOrderDetail(orderId, user.getMemberId());
    }

    @Override
    @PatchMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") Long orderId,
            @AuthenticationPrincipal CustomUserDetails user) {
        orderService.deleteOrder(orderId, user.getMemberId());
    }
}
