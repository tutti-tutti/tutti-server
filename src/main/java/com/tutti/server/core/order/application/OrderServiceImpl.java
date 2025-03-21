package com.tutti.server.core.order.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.order.domain.CreatedByType;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.domain.OrderItem;
import com.tutti.server.core.order.domain.OrderStatus;
import com.tutti.server.core.order.infrastructure.OrderHistoryRepository;
import com.tutti.server.core.order.infrastructure.OrderItemRepository;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.order.payload.response.OrderDetailResponse;
import com.tutti.server.core.order.payload.response.OrderItemResponse;
import com.tutti.server.core.order.payload.response.OrderResponse;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.store.domain.Store;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final MemberRepository memberRepository;
    private final ProductItemRepository productItemRepository;

    @Override
    @Transactional
    public void createOrder(OrderCreateRequest orderCreateRequest) {
        // 1. 주문번호 생성
        String orderNumber = generateOrderNumber();

        // 2. 회원 조회
        Member member = memberRepository.findOne(orderCreateRequest.memberId());

        // 3. 주문 상품 정보 조회 및 검증
        List<ProductItem> productItems = getProductItems(orderCreateRequest.orderItems());

        // 4. 총 금액 계산
        int totalAmount = calculateTotalProductAmount(orderCreateRequest.orderItems(),
                productItems);

        // 5. 주문 생성
        Order order = orderRepository.save(orderCreateRequest.toEntity(member, orderNumber,
                orderCreateRequest.orderItems().size(), totalAmount,
                OrderStatus.WAITING_FOR_PAYMENT.name()));

        // 6. 주문 아이템 생성
        List<OrderItem> orderItems = orderItemRepository.saveAll(
                createOrderItems(order, orderCreateRequest.orderItems(), productItems));

        // 7. 주문 이력 생성
        orderHistoryRepository.save(
                orderCreateRequest.toEntity(order, CreatedByType.MEMBER, member.getId(),
                        OrderStatus.WAITING_FOR_PAYMENT.name()));
    }

    @Override
    public String generateOrderNumber() {
        LocalDateTime now = LocalDateTime.now();
        String datePart = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = UUID.randomUUID().toString().substring(0, 8);
        return datePart + "-" + randomPart;
    }

    @Override
    public List<ProductItem> getProductItems(
            List<OrderCreateRequest.OrderItemRequest> orderItemRequests) {
        List<Long> productItemIds = orderItemRequests.stream()
                .map(OrderCreateRequest.OrderItemRequest::productItemId)
                .toList();

        List<ProductItem> productItems = productItemRepository.findAllById(productItemIds);

        if (productItems.size() != productItemIds.size()) {
            throw new DomainException(ExceptionType.NON_EXISTENT_PRODUCT_INCLUDE);
        }

        return productItems;
    }

    @Override
    public int calculateTotalProductAmount(
            List<OrderCreateRequest.OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems) {
        int totalProductAmount = 0;

        for (OrderCreateRequest.OrderItemRequest orderItemRequest : orderItemRequests) {
            ProductItem productItem = findProductItemById(productItems,
                    orderItemRequest.productItemId());
            totalProductAmount += productItem.getSellingPrice() * orderItemRequest.quantity();
        }
        return totalProductAmount;
    }

    @Override
    public List<OrderItem> createOrderItems(
            Order order,
            List<OrderCreateRequest.OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderCreateRequest.OrderItemRequest orderItemRequest : orderItemRequests) {
            ProductItem productItem = findProductItemById(productItems,
                    orderItemRequest.productItemId());

            orderItems.add(orderItemRequest.toEntity(order, productItem));
        }

        return orderItems;
    }

    @Override
    public ProductItem findProductItemById(List<ProductItem> productItems, Long productItemId) {
        return productItems.stream()
                .filter(item -> item.getId().equals(productItemId))
                .findFirst()
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_MISMATCH));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders(Long memberId) {
        return orderRepository.findAllByMemberIdAndDeleteStatusFalse(memberId)
                .stream()
                .map(order -> OrderResponse.fromEntity(order,
                        orderItemRepository.findAllByOrderId(order.getId())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(Long orderId) {
        // 주문 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 상품 조회
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);

        // 첫 번째 상품의 스토어 정보 조회 (모든 상품이 같은 스토어라고 가정)
        Store store = null;
        if (!orderItems.isEmpty()) {
            ProductItem firstProductItem = orderItems.get(0).getProductItem();
            store = firstProductItem.getProduct().getStoreId();
        }

        // 결제 정보 (실제 구현에서는 결제 서비스에서 가져와야 할 수 있음)
        int totalProductAmount = order.getTotalAmount();
        int discountAmount = 0; // 할인 금액 (실제 구현에서는 계산 필요)
        int deliveryFee = order.getDeliveryFee();

        // 주문 상태에 따른 날짜 정보 설정
        LocalDateTime paidAt = null;
        LocalDateTime completedAt = order.getCompletedAt();

        if (OrderStatus.valueOf(order.getOrderStatus()) == OrderStatus.ORDER_RECEIVED
                || OrderStatus.valueOf(order.getOrderStatus()) == OrderStatus.ORDER_COMPLETED) {
            paidAt = order.getCreatedAt().plusMinutes(5); // 예시: 생성 후 5분 뒤 결제 완료
        }

        // 응답 객체 생성
        return OrderDetailResponse.builder()
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .totalProductAmount(totalProductAmount)
                .discountAmount(discountAmount)
                .deliveryFee(deliveryFee)
                .totalAmount(totalProductAmount - discountAmount + deliveryFee)
                .paymentType(order.getPaymentType())
                .orderedAt(order.getCreatedAt())
                .paidAt(paidAt)
                .deliveredAt(null)
                .completedAt(completedAt)
                .orderItems(orderItems.stream()
                        .map(OrderItemResponse::fromEntity)
                        .toList())
                .storeName(store != null ? store.getName() : "")
                .build();
    }
}
