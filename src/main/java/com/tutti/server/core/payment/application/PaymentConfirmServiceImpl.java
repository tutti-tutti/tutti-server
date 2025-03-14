package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentMethodRepository;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.ParsedTossApiResponse;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class PaymentConfirmServiceImpl implements PaymentConfirmService {

    private final RestClient restClient;
    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Value("${toss.secret-key}")
    private String widgetSecretKey;

    @Override
    @Transactional
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

