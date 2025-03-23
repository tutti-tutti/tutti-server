package com.tutti.server.core.payment.application;


import com.tutti.server.core.order.application.OrderService;
import com.tutti.server.core.order.domain.CreatedByType;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.infrastructure.OrderHistoryRepository;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.request.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.request.PaymentRequest;
import com.tutti.server.core.payment.payload.response.ParsedTossApiResponse;
import com.tutti.server.core.payment.payload.response.PaymentResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;
    private final TossPaymentService tossPaymentService;
    private final PaymentHistoryService paymentHistoryService;
    private final OrderHistoryRepository orderHistoryRepository;

    //1. 결제 요청
    @Override
    @Transactional
    public PaymentResponse requestPayment(PaymentRequest request, Long authMemberId) {
        Order order = getValidOrder(request.orderNumber(), authMemberId);
        validateOrderAmountAndName(order, request);
        Payment payment = validateOrReusePayment(order, request);

        return PaymentResponse.fromEntity(payment);
    }

    //2. 결제 승인
    @Override
    @Transactional
    public Map<String, Object> confirmPayment(PaymentConfirmRequest request, Long authMemberId) {
        Payment payment = getValidPayment(request.orderId(), authMemberId);
        Map<String, Object> response = tossPaymentService.confirmPayment(request);
        ParsedTossApiResponse parsedResponse = ParsedTossApiResponse.fromResponse(response);
        updatePayment(payment, parsedResponse);

        // Order 조회
        var order = payment.getOrder();

        // 주문 상태 및 일시 업데이트
        order.updateOrderStatus(PaymentStatus.DONE.name());
        order.updateCompletedAt(LocalDateTime.now());
        order.updatePaidAt(payment.getCompletedAt());

        // 주문 이력 업데이트
        orderService.createOrderHistory(order, CreatedByType.MEMBER, authMemberId);

        return response;
    }

    private void validateOrderAmountAndName(Order order, PaymentRequest request) {
        if (order.getTotalAmount() != request.amount()) {
            throw new DomainException(ExceptionType.PAYMENT_AMOUNT_MISMATCH);
        }
        if (!order.getOrderName().equals(request.orderName())) {
            throw new DomainException(ExceptionType.ORDER_NAME_MISMATCH);
        }
    }

    // 1-2. 기존 결제 여부 검증 메서드
    private Payment validateOrReusePayment(Order order, PaymentRequest request) {
        return paymentRepository.findByOrderId(order.getId())
                .map(existingPayment -> validateExistingPayment(existingPayment, request))
                .orElseGet(() -> createNewPayment(order, request));
    }

    private Payment validateExistingPayment(Payment existingPayment, PaymentRequest request) {
        // 주문명이 다른 경우 예외
        if (!existingPayment.getOrderName().equals(request.orderName())) {
            throw new DomainException(ExceptionType.ORDER_NAME_MISMATCH);
        }
        PaymentStatus status = PaymentStatus.valueOf(existingPayment.getPaymentStatus());
        // 재시도 허용 가능한 상태
        if (status == PaymentStatus.READY || status == PaymentStatus.IN_PROGRESS) {
            return existingPayment;
        }
        // 그 외 상태는 이미 결제된 것으로 간주
        throw new DomainException(ExceptionType.PAYMENT_ALREADY_EXISTS);
    }

    private Payment createNewPayment(Order order, PaymentRequest request) {
        Payment payment = PaymentRequest.toEntity(
                order,
                order.getMember(),
                request.amount(),
                request.orderName(),
                order.getOrderNumber());

        return paymentRepository.save(payment);
    }

    // 결제 테이블 업데이트
    private void updatePayment(Payment payment, ParsedTossApiResponse parsedResponse) {

        confirmPaymentDomain(payment, parsedResponse);
        paymentRepository.save(payment);
        paymentHistoryService.savePaymentHistory(payment);
    }

    // 결제 승인 후 테이블 업데이트
    private void confirmPaymentDomain(Payment payment, ParsedTossApiResponse parsedResponse) {
        payment.afterConfirmUpdatePayment(
                parsedResponse.paymentKey(),
                parsedResponse.status(),
                parsedResponse.approvedAt(),
                parsedResponse.amount()
        );
    }

    private Payment getValidPayment(String orderNumber, Long memberId) {
        return paymentRepository.findByOrderNumberAndMemberId(orderNumber, memberId)
                .orElseThrow(() -> new DomainException(ExceptionType.UNAUTHORIZED_ERROR));
    }

    private Order getValidOrder(String orderNumber, Long memberId) {
        return orderRepository.findByOrderNumberAndMemberId(orderNumber, memberId)
                .orElseThrow(() -> new DomainException(ExceptionType.UNAUTHORIZED_ERROR));
    }
}
