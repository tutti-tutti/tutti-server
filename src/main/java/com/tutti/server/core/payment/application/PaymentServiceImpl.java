package com.tutti.server.core.payment.application;


import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentHistory;
import com.tutti.server.core.payment.domain.PaymentMethod;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentHistoryRepository;
import com.tutti.server.core.payment.infrastructure.PaymentMethodRepository;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.ParsedTossApiResponse;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final RestClient restClient;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;

    @Value("${toss.secret-key}")
    private String widgetSecretKey;

    // 결제 요청
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

        // 1. 요청 바디 생성
        Map<String, Object> requestBody = buildRequestBody(request);

        // 2. 결제 정보 조회
        Payment payment = checkPayment(
                request.orderId()); //orderId는 토스orderId입니다. orderId로 요청을 보내야 함.

        // 3. Toss Payments API 호출
        Map<String, Object> response = sendConfirmRequest(requestBody);

        // 4. API 응답을 DTO로 변환(형변환)
        ParsedTossApiResponse parsedResponse = ParsedTossApiResponse.fromResponse(response);

        // 5. 결제 상태 업데이트 및 결제 이력 저장.
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
        if (paymentRepository.existsByOrderId(orderId)) {
            throw new DomainException(ExceptionType.PAYMENT_ALREADY_PROCESSING);
        }
    }

    // 1-3. 결제 객체 생성 및 저장 메서드
    private Payment createAndSavePayment(Order order, PaymentRequest request) {
        Payment payment = PaymentRequest.toEntity(order, order.getMember(), request.amount(),
                request.orderName(), order.getOrderNumber());
        return paymentRepository.save(payment);
    }

    // 2-1. 결제 승인
    private Map<String, Object> buildRequestBody(PaymentConfirmRequest request) {
        return Map.of(
                "paymentKey", request.paymentKey(),
                "orderId", request.orderId(), // orderNumber(토스)
                "amount", request.amount()
        );
    }

    // 2-2. 결제 테이블에 주문이 결제 중인지 확인.
    private Payment checkPayment(String orderId) {
        return paymentRepository.findByOrderNumber(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));
    }

    // 2-3. Toss의 결제 승인 Api 호출
    private Map<String, Object> sendConfirmRequest(Map<String, Object> requestBody) {
        return restClient.post()
                .uri("https://api.tosspayments.com/v1/payments/confirm")
                .headers(headers -> headers.setBasicAuth(widgetSecretKey, ""))
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    // 2-4. 결제 테이블 업데이트
    private void updatePayment(Payment payment, ParsedTossApiResponse parsedResponse) {
        // 결제 수단을 enum값으로 변환해주기 위한 1차작업.
        PaymentMethod paymentMethod = findPaymentMethod(parsedResponse.method());

        // (2) 결제 확정 (Payment 엔티티에서 상태 및 필드 갱신)
        confirmPaymentDomain(payment, paymentMethod, parsedResponse);

        // 3. 엔티티에 결제수단 + 결제 상태 + 결제 키 + 시간 + 금액 한 번에 세팅
        payment.confirmPayment(
                paymentMethod,                         // 결제수단 엔티티
                parsedResponse.paymentKey(),
                PaymentStatus.valueOf(parsedResponse.status()),
                parsedResponse.approvedAt(),
                parsedResponse.amount()
        );
        paymentRepository.save(payment);

        // 4. 결제 이력 등록
        recordPaymentHistory(payment);
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
        payment.confirmPayment(
                method,
                parsedResponse.paymentKey(),
                PaymentStatus.valueOf(parsedResponse.status()),
                parsedResponse.approvedAt(),
                parsedResponse.amount()
        );
    }

    // 2-7. 결제 이력 남기기
    private void recordPaymentHistory(Payment payment) {
        // 이전 이력의 상태를 false로 바꾸는
        paymentHistoryRepository.findByPaymentIdAndLatestStatus(payment.getId(), true)
                .ifPresent(oldHistory -> {
                    oldHistory.markAsOldHistory(); // 현재 결제 승인할 때의 id
                    paymentHistoryRepository.save(oldHistory);
                });

        // 새 이력 생성
        PaymentHistory newHistory = PaymentHistory.builder()
                .payment(payment)
                .paymentStatus(payment.getPaymentStatus())
                .latestStatus(true)
                .statusUpdatedAt(LocalDateTime.now())
                .build();

        paymentHistoryRepository.save(newHistory);
    }
}
