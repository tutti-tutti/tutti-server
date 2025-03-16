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
import jakarta.transaction.Transactional;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final RestClient restClient;

    @Value("${toss.secret-key}")
    private String widgetSecretKey;

    // 결제 요청
    @Override
    @Transactional
    public PaymentResponse requestPayment(PaymentRequest request) {

        Order order = validateOrderAndPaymentAmount(request.orderNumber(), request.amount());
        validateDuplicatePayment(order.getId());
        Payment savedPayment = createAndSavePayment(order, request);
        return PaymentResponse.fromEntity(savedPayment);
    }

    private Order validateOrderAndPaymentAmount(String orderNumber, int amount) {
        return orderRepository.findByOrderNumber(orderNumber)
                .map(order -> {
                    if (order.getTotalAmount() != amount) {
                        throw new DomainException(ExceptionType.PAYMENT_AMOUNT_MISMATCH);
                    }
                    return order;
                })
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));
    // 결제 승인
    @Override
    @org.springframework.transaction.annotation.Transactional
    public Map<String, Object> confirmPayment(PaymentConfirmRequest request) {

        // 1. 요청 바디 생성
        Map<String, Object> requestBody = buildRequestBody(request);

        // 2. 결제 정보 조회
        Payment payment = getPayment(request.orderId()); //orderId는 토스orderId입니다.

        // 3. Toss Payments API 호출
        Map<String, Object> response = sendConfirmRequest(requestBody);

        // 4. API 응답을 DTO로 변환(형변환)
        ParsedTossApiResponse parsedResponse = ParsedTossApiResponse.fromResponse(response);

        // 5. 결제 상태 업데이트
        updatePayment(payment, parsedResponse);

        return response;
    }

    // 주문 정보 검증 메서드
    private Order validateOrder(String tossOrderId) {
        return orderRepository.findByOrderNumber(tossOrderId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 orderId에 해당하는 주문을 찾을 수 없습니다: " + tossOrderId));
    }

    // 기존 결제 여부 검증 메서드
    private void validateDuplicatePayment(Long orderId) {
        if (paymentRepository.existsByOrderId(orderId)) {
            throw new DomainException(ExceptionType.PAYMENT_ALREADY_PROCESSING);
        }
    }

    // 결제 객체 생성 및 저장 메서드
    private Payment createAndSavePayment(Order order, PaymentRequest request) {
        Payment payment = PaymentRequest.toEntity(order, order.getMember(), request.amount(),
                request.orderName(), order.getOrderNumber());
        return paymentRepository.save(payment);
    }

    private Map<String, Object> buildRequestBody(PaymentConfirmRequest request) {
        return Map.of(
                "paymentKey", request.paymentKey(),
                "orderId", request.orderId(),
                "amount", request.amount()
        );
    }

    private Payment getPayment(String orderId) {
        return paymentRepository.findByTossOrderId(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));
    }

    private Map<String, Object> sendConfirmRequest(Map<String, Object> requestBody) {
        return restClient.post()
                .uri("https://api.tosspayments.com/v1/payments/confirm")
                .headers(headers -> headers.setBasicAuth(widgetSecretKey, ""))
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    private void updatePayment(Payment payment, ParsedTossApiResponse parsedResponse) {
        payment.updatePayment(
                parsedResponse.paymentKey(),
                PaymentStatus.valueOf(parsedResponse.status()),
                parsedResponse.approvedAt(),
                parsedResponse.amount()
        );
        paymentRepository.save(payment);
    }
}
