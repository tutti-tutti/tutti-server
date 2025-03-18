package com.tutti.server.core.order.api;

import com.tutti.server.core.order.application.OrderService;
import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.order.payload.response.OrderResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderApi implements OrderApiSpec {

    private final OrderService orderService;

    @Override
    @PostMapping
    public void createOrder(@Valid @RequestBody OrderCreateRequest request) {
        orderService.createOrder(request);
    }

    // 주문 전체 조회
    @Override
    @GetMapping
    public List<OrderResponse> getOrders(@RequestBody Long memberId) {
        return orderService.getOrders(memberId);
    }
}
