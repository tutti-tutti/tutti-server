package com.tutti.server.core.order.application;

import com.tutti.server.core.order.domain.CreatedByType;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.order.payload.request.OrderItemRequest;
import com.tutti.server.core.order.payload.request.OrderSheetRequest;
import com.tutti.server.core.order.payload.response.CursorBasedOrdersResponse;
import com.tutti.server.core.order.payload.response.OrderDetailResponse;
import com.tutti.server.core.order.payload.response.OrderItemResponse;
import com.tutti.server.core.order.payload.response.OrderSheetResponse;
import com.tutti.server.core.payment.payload.request.PaymentRequest;
import com.tutti.server.core.product.domain.ProductItem;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;

public interface OrderService {

    OrderSheetResponse getOrderSheet(OrderSheetRequest request);

    void validateProductItems(List<OrderItemRequest> requests);

    int calculateTotalProductAmount(
            List<OrderItemRequest> requests);

    int calculateTotalDiscountAmount(
            List<OrderItemRequest> requests);

    int calculateOrderTotal(
            List<OrderItemRequest> requests,
            BiFunction<ProductItem, Integer, Integer> calculator);

    List<OrderItemResponse> createOrderItemResponses(List<OrderItemRequest> requests);

    LocalDate generateRandomDays();

    PaymentRequest createOrder(OrderCreateRequest request, Long memberId);

    String generateOrderSheetNo();

    String generateOrderName(OrderCreateRequest request);

    void createOrderItems(Order order, List<OrderItemRequest> requests);

    void createOrderHistory(Order order, CreatedByType createdByType, Long createdById);

    CursorBasedOrdersResponse getOrders(
            Long memberId, LocalDateTime cursorCreatedAt, Long cursorId, int size);

    OrderDetailResponse getOrderDetail(Long orderId, Long memberId);

    void deleteOrder(Long orderId, Long memberId);

    Order getOrder(Long orderId, Long memberId);
}
