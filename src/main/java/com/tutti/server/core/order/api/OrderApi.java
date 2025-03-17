package com.tutti.server.core.order.api;

import com.tutti.server.core.order.application.OrderService;
import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.order.payload.response.OrderListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Override
    @GetMapping
    public PagedModel<OrderListResponse> getOrders(@AuthenticationPrincipal UserDetails user,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "15") int pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return orderService.getOrders(user.getUsername(), pageable);
    }
}
