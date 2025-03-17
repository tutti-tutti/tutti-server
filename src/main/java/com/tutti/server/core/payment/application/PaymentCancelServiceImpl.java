//package com.tutti.server.core.payment.application;
//
//import com.tutti.server.core.payment.domain.Payment;
//import com.tutti.server.core.payment.domain.PaymentHistory;
//import com.tutti.server.core.payment.domain.PaymentStatus;
//import com.tutti.server.core.payment.infrastructure.PaymentHistoryRepository;
//import com.tutti.server.core.payment.infrastructure.PaymentRepository;
//import com.tutti.server.core.payment.payload.PaymentCancelRequest;
//import com.tutti.server.core.payment.payload.TossPaymentsCancelRequest;
//import com.tutti.server.core.payment.payload.TossPaymentsCancelResponse;
//import com.tutti.server.core.support.exception.DomainException;
//import com.tutti.server.core.support.exception.ExceptionType;
//import java.time.LocalDateTime;
//import java.util.Base64;
//import java.util.Map;
//import java.util.UUID;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestClient;
//
//@Service
//@RequiredArgsConstructor
//public class PaymentCancelServiceImpl implements PaymentService {
//
//    private final PaymentRepository paymentRepository;
//    private final PaymentHistoryRepository paymentHistoryRepository;
//    private final RestClient restClient; //TODO 머지 후 추가.
//
//    @Value("${toss.secret-key}")
//    private String widgetSecretKey;
//
//    @Value("${toss.cancel-api}")
//    private String tossPaymentsCancelUrl;
//
//    @Override
//    @Transactional
//    public void paymentCancel(PaymentCancelRequest request) {
//
//        // 1. 결제 정보 조회
//        Payment payment = findPaymentByOrderId(request.orderId());
//
//        // 2. TossPayments API 호출 및 응답 파싱
//        Map<String, Object> response = requestTossCancelApi(payment.getTossPaymentKey(),
//                request.getCancelReason());
//        TossPaymentsCancelResponse parsedResponse = parseTossCancelResponse(response);
//
//        // 3. 결제 상태 업데이트
//        updatePaymentStatus(payment, parsedResponse);
//
//        // 4. 결제 이력 저장
//        savePaymentHistory(payment);
//
//    }
//
//    /**
//     * 주문 ID를 사용하여 결제 정보를 조회
//     */
//    private Payment findPaymentByOrderId(Long orderId) {
//        return paymentRepository.findByOrderId(orderId)
//                .orElseThrow(() -> new DomainException(ExceptionType.PAYMENT_NOT_FOUND));
//    }
//
//    private Map<String, Object> requestTossCancelApi(String paymentKey, String cancelReason) {
//        String cancelUrl = tossPaymentsCancelUrl + "/" + paymentKey + "/cancel";
//
//        HttpHeaders headers = createTossApiHeaders();
//
//        TossPaymentsCancelRequest tossRequest = new TossPaymentsCancelRequest(cancelReason);
//
//        return restClient.method(HttpMethod.POST)
//                .uri(cancelUrl)
//                .headers(httpHeaders -> httpHeaders.addAll(headers))
//                .body(tossRequest)
//                .retrieve()
//                .body(new ParameterizedTypeReference<>() {
//                });
//    }
//
//    /**
//     * TossPayments API 응답을 파싱하여 DTO로 변환
//     */
//    private TossPaymentsCancelResponse parseTossCancelResponse(Map<String, Object> response) {
//        return TossPaymentsCancelResponse.fromResponse(response);
//    }
//
//    /**
//     * 결제 상태를 취소로 업데이트
//     */
//    private void updatePaymentStatus(Payment payment, TossPaymentsCancelResponse response) {
//        payment.cancelPayment(response.canceledAt());
//        paymentRepository.save(payment);
//    }
//
//    /**
//     * 결제 취소 이력 저장
//     */
//    private void savePaymentHistory(Payment payment) {
//        PaymentHistory paymentHistory = PaymentHistory.builder()
//                .payment(payment)
//                .paymentStatus(PaymentStatus.PAYMENT_CANCELED)
//                .latestStatus(true)
//                .statusUpdatedAt(LocalDateTime.now())
//                .build();
//
//        paymentHistoryRepository.save(paymentHistory);
//    }
//
//    /**
//     * TossPayments API 헤더 생성
//     */
//    private HttpHeaders createTossApiHeaders() {
//        String secretKeyBase64 = Base64.getEncoder()
//                .encodeToString((widgetSecretKey + ":").getBytes());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(secretKeyBase64);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Idempotency-Key", UUID.randomUUID().toString());
//
//        return headers;
//    }
//
//}
