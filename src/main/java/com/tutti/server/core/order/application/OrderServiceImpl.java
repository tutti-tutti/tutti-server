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
import com.tutti.server.core.order.payload.response.OrderResponse;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductItemRepository productItemRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    @Override
    @Transactional
    public void createOrder(OrderCreateRequest request) {
        // 1. 회원 조회
        Member member = memberRepository.findOne(request.memberId());

        // 2. 주문번호 생성
        String orderNumber = generateOrderNumber();

        // 3. 주문명 생성
        String orderName = generateOrderName(request);

        // 4. 주문 상품 정보 조회 및 검증
        List<ProductItem> productItems = getProductItems(request.orderItems());

        // 5. 총 상품 금액 계산
        int totalProductAmount = calculateTotalProductAmount(request.orderItems(),
                productItems);

        // 6. 총 할인 금액 계산
        int totalDiscountAmount = calculateTotalDiscountAmount(request.orderItems(), productItems);

        // 7 .배송비 (추후 배송비 측정 로직 추가 해야 됨)
        int deliveryFee = 0;

        // 8. 총 결제 금액 = 총 상품 금액 + 배송비 (할인 금액은 이미 totalProductAmount(sellingPrice)에 반영이 되어 있음)
        int totalAmount = totalProductAmount + deliveryFee;

        // 9. 주문 생성
        Order order = orderRepository.save(
                request.toEntity(member, OrderStatus.WAITING_FOR_PAYMENT.name(), orderNumber,
                        orderName, request.orderItems().size(), totalProductAmount,
                        totalDiscountAmount, deliveryFee, totalAmount
                ));

        // 10. 주문 아이템 생성
        orderItemRepository.saveAll(createOrderItems(order, request.orderItems(), productItems));

        // 11. 주문 이력 생성
        orderHistoryRepository.save(
                request.toEntity(order, OrderStatus.WAITING_FOR_PAYMENT.name(),
                        CreatedByType.MEMBER, member.getId()));
    }

    @Override
    public String generateOrderNumber() {
        LocalDateTime now = LocalDateTime.now();
        String datePart = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = UUID.randomUUID().toString().substring(0, 8);
        return datePart + "-" + randomPart;
    }

    @Override
    public String generateOrderName(OrderCreateRequest request) {
        // OrderCreateRequest 는 orderItem 이 생성되기 전이라는 것을 명심하자.
        // 첫 번째 상품의 ID 가져오기
        Long firstProductItemId = request.orderItems().get(0).productItemId();

        // 첫 번째 상품의 정보 조회
        String firstProductName = productItemRepository.findOne(firstProductItemId)
                .getProduct().getName();

        // 주문 아이템 개수
        int orderItemCount = request.orderItems().size();

        // 주문 아이템이 1개인 경우 "외 N건" 부분 생략
        if (orderItemCount == 1) {
            return firstProductName;
        }

        // "상품명 외 N건" 형식으로 주문명 생성
        return firstProductName + " 외 " + (orderItemCount - 1) + "건";
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
        return calculateOrderTotal(orderItemRequests, productItems,
                (productItem, quantity) -> productItem.getSellingPrice() * quantity);
    }

    @Override
    public int calculateTotalDiscountAmount(
            List<OrderCreateRequest.OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems) {
        return calculateOrderTotal(orderItemRequests, productItems,
                (productItem, quantity) -> productItem.getDiscountPrice());
    }

    /**
     * 주문 항목에 대한 계산을 수행하는 공통 메서드
     *
     * @param orderItemRequests 주문 상품 요청 DTO 목록
     * @param productItems      판매 상품 목록
     * @param calculator        계산 로직을 담당하는 함수형 인터페이스
     * @return 계산된 총액
     */
    public int calculateOrderTotal(
            List<OrderCreateRequest.OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems,
            BiFunction<ProductItem, Integer, Integer> calculator) {
        int total = 0;

        for (OrderCreateRequest.OrderItemRequest orderItemRequest : orderItemRequests) {
            ProductItem productItem = findProductItemById(productItems,
                    orderItemRequest.productItemId());
            total += calculator.apply(productItem, orderItemRequest.quantity());
        }

        return total;
    }

    @Override
    public List<OrderItem> createOrderItems(
            Order order,
            List<OrderCreateRequest.OrderItemRequest> orderItemRequests,
            List<ProductItem> productItems) {
        return orderItemRequests.stream()
                .map(orderItemRequest -> {
                    ProductItem productItem = findProductItemById(productItems,
                            orderItemRequest.productItemId());
                    return orderItemRequest.toEntity(order, productItem);
                })
                .toList();
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
                                orderItemRepository.findAllByOrderId(order.getId())
                        )
                )
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(Long orderId) {
        // 주문 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 상품 조회
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);

        return OrderDetailResponse.fromEntity(order, orderItems);
    }
}
