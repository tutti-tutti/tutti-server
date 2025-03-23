package com.tutti.server.core.order.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.order.application.OrderService;
import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.order.payload.request.OrderPageRequest;
import com.tutti.server.core.order.payload.response.OrderDetailResponse;
import com.tutti.server.core.order.payload.response.OrderPageResponse;
import com.tutti.server.core.order.payload.response.OrderResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @PostMapping
    public void createOrder(@AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody OrderCreateRequest request) {
        orderService.createOrder(user.getMemberId(), request);
    }

    @Override
    @GetMapping
    public List<OrderResponse> getOrders(@AuthenticationPrincipal CustomUserDetails user) {
        return orderService.getOrders(user.getMemberId());
    }

    @Override
    @GetMapping("/{orderId}")
    public OrderDetailResponse getOrderDetail(@AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("orderId") Long orderId) {
        return orderService.getOrderDetail(user.getMemberId(), orderId);
    }

    @Override
    @PatchMapping("/{orderId}")
    public void deleteOrder(@AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(user.getMemberId(), orderId);
    }
}
