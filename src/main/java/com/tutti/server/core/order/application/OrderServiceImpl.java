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
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
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
}
