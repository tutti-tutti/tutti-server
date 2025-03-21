package com.tutti.server.core.payment.application;


import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
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
    private final TossPaymentService tossPaymentService;
    private final PaymentHistoryService paymentHistoryService;

    //1. 결제 요청
    @Override
    @Transactional
    public PaymentResponse requestPayment(PaymentRequest request) {

        Order order = validateOrderRequest(request);
        Payment payment = validateOrReusePayment(order, request);
        return PaymentResponse.fromEntity(payment);
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
    private Order validateOrderRequest(PaymentRequest request) {
        return orderRepository.findByOrderNumber(request.orderId())
                .map(order -> {
                    if (order.getTotalAmount() != request.amount()) {
                        throw new DomainException(ExceptionType.PAYMENT_AMOUNT_MISMATCH);
                    }
                    if (!order.getOrderName().equals(request.orderName())) {
                        throw new DomainException(ExceptionType.ORDER_NAME_MISMATCH);
                    }
                    return order;
                })
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));
    }

    // 1-2. 기존 결제 여부 검증 메서드
    private Payment validateOrReusePayment(Order order, PaymentRequest request) {
        return paymentRepository.findByOrderId(order.getId())
                .map(existingPayment -> {
                    PaymentStatus status = PaymentStatus.valueOf(
                            existingPayment.getPaymentStatus());

                    // 재시도 허용할 수 있도록 추가(결제 요청 하고 x눌르고 다시 요청할 수 있도록)
                    if (status == PaymentStatus.READY || status == PaymentStatus.IN_PROGRESS) {
                        return existingPayment;
                    }
                    throw new DomainException(ExceptionType.PAYMENT_ALREADY_EXISTS);
                })
                .orElseGet(() -> {
                    Payment payment = PaymentRequest.toEntity(order, order.getMember(),
                            request.amount(),
                            request.orderName(), order.getOrderNumber());
                    return paymentRepository.save(payment);
                });
    }

    // 2-1. 결제 테이블에 주문이 결제 중인지 확인.
    private Payment checkPayment(String orderId) {
        return paymentRepository.findByOrderNumber(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));
    }

    // 2-4. 결제 테이블 업데이트
    private void updatePayment(Payment payment, ParsedTossApiResponse parsedResponse) {

        confirmPaymentDomain(payment, parsedResponse);
        paymentRepository.save(payment);
        paymentHistoryService.savePaymentHistory(payment);
    }

    // 2-6. 결제 승인 후 테이블 업데이트
    private void confirmPaymentDomain(Payment payment, ParsedTossApiResponse parsedResponse) {
        payment.afterConfirmUpdatePayment(
                parsedResponse.paymentKey(),
                parsedResponse.status(),
                parsedResponse.approvedAt(),
                parsedResponse.amount()
        );
    }
}
