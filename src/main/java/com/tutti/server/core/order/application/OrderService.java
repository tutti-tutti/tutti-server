package com.tutti.server.core.order.application;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderItem;
import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.order.payload.request.OrderItemRequest;
import com.tutti.server.core.order.payload.request.OrderPageRequest;
import com.tutti.server.core.order.payload.response.OrderDetailResponse;
import com.tutti.server.core.order.payload.response.OrderItemResponse;
import com.tutti.server.core.order.payload.response.OrderPageResponse;
import com.tutti.server.core.order.payload.response.OrderResponse;
import com.tutti.server.core.product.domain.ProductItem;
import java.util.List;
import java.util.function.BiFunction;

public interface OrderService {

    OrderPageResponse getOrderPage(OrderPageRequest request);

    List<OrderItemResponse> createOrderItemResponses(
            List<OrderItemRequest> requests,
            List<ProductItem> productItems);

    void createOrder(OrderCreateRequest request);

    String generateOrderNumber();

    String generateOrderName(OrderCreateRequest request);

    List<ProductItem> getProductItems(List<OrderItemRequest> orderItemRequests);

    int calculateTotalProductAmount(
            List<OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems);

    int calculateTotalDiscountAmount(
            List<OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems);

    int calculateOrderTotal(
            List<OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems,
            BiFunction<ProductItem, Integer, Integer> calculator);

    List<OrderItem> createOrderItems(Order order,
            List<OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems);

    ProductItem findProductItemById(List<ProductItem> productItems, Long productItemId);

    List<OrderResponse> getOrders(Long memberId);

    OrderDetailResponse getOrderDetail(Long orderId);

    void deleteOrder(Long memberId, Long orderId);
}
