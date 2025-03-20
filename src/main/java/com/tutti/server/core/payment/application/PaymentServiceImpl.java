package com.tutti.server.core.payment.application;


import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentMethod;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentMethodRepository;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.ParsedTossApiResponse;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final TossPaymentService tossPaymentService;
    private final PaymentHistoryService paymentHistoryService;

    //1. 결제 요청
    @Override
    @Transactional
    public PaymentResponse requestPayment(PaymentRequest request) {

        Order order = validateOrderAndPaymentAmount(request.orderId(), request.amount());
        validateDuplicatePayment(order.getId());
        Payment savedPayment = createAndSavePayment(order, request);
        return PaymentResponse.fromEntity(savedPayment);
    }

    //2. 결제 승인
    @Override
    @Transactional
    public Map<String, Object> confirmPayment(PaymentConfirmRequest request) {

        Payment payment = checkPayment(request.orderId());
        Map<String, Object> response = tossPaymentService.confirmPayment(request);
        ParsedTossApiResponse parsedResponse = ParsedTossApiResponse.fromResponse(response);
        updatePayment(payment, parsedResponse);
        return response;
    }

    // 1-1. 주문 및 결제 금액 검증
    private Order validateOrderAndPaymentAmount(String orderNumber, int amount) {
        return orderRepository.findByOrderNumber(orderNumber)
                .map(order -> {
                    if (order.getTotalAmount() != amount) {
                        throw new DomainException(ExceptionType.PAYMENT_AMOUNT_MISMATCH);
                    }
                    return order;
                })
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));
    }

    // 1-2. 기존 결제 여부 검증 메서드
    private void validateDuplicatePayment(Long orderId) {
        paymentRepository.findByOrderId(orderId)
                .ifPresent(payment -> {
                    // 1결제 상태가 IN_PROGRESS(결제 진행 중)인지 추가.
                    if (payment.getPaymentStatus().equals(PaymentStatus.IN_PROGRESS.name())) {
                        throw new DomainException(ExceptionType.PAYMENT_ALREADY_PROCESSING);
                    }
                });
    }

    // 1-3. 결제 객체 생성 및 저장 메서드
    private Payment createAndSavePayment(Order order, PaymentRequest request) {
        Payment payment = PaymentRequest.toEntity(order, order.getMember(), request.amount(),
                request.orderName(), order.getOrderNumber());
        return paymentRepository.save(payment);
    }

    // 2-1. 결제 테이블에 주문이 결제 중인지 확인.
    private Payment checkPayment(String orderId) {
        return paymentRepository.findByOrderNumber(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));
    }

    // 2-4. 결제 테이블 업데이트
    private void updatePayment(Payment payment, ParsedTossApiResponse parsedResponse) {

        PaymentMethod paymentMethod = findPaymentMethod(parsedResponse.method());

        confirmPaymentDomain(payment, paymentMethod, parsedResponse);

        paymentRepository.save(payment);
        paymentHistoryService.savePaymentHistory(payment);
    }

    // 2-5. 한글로 들어온 결제수단을 enum값으로 변환 후 반환.
    private PaymentMethod findPaymentMethod(String methodName) {
        PaymentMethodType methodType = PaymentMethodType.fromString(methodName);
        return paymentMethodRepository.findByMethodType(methodType)
                .orElseThrow(() -> new DomainException(ExceptionType.INVALID_METHOD));
    }

    // 2-6. 결제 승인 후 테이블 업데이트
    private void confirmPaymentDomain(Payment payment,
            PaymentMethod method,
            ParsedTossApiResponse parsedResponse) {
        payment.afterConfirmUpdatePayment(
                method,
                parsedResponse.paymentKey(),
                parsedResponse.status(),
                parsedResponse.approvedAt(),
                parsedResponse.amount()
        );
    }
}
