package com.tutti.server.core.order.application;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderItem;
import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.order.payload.response.OrderListResponse;
import com.tutti.server.core.product.domain.ProductItem;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface OrderService {

    void createOrder(OrderCreateRequest orderCreateRequest);

    String generateOrderNumber();

    List<ProductItem> getProductItems(List<OrderCreateRequest.OrderItemRequest> orderItemRequests);

    int calculateTotalProductAmount(
            List<OrderCreateRequest.OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems);

    List<OrderItem> createOrderItems(Order order,
            List<OrderCreateRequest.OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems);

    ProductItem findProductItemById(List<ProductItem> productItems, Long productItemId);

    PagedModel<OrderListResponse> getOrders(String memberEmail, Pageable pageable);
}
