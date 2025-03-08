package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RestClient restClient;
    private final PaymentRepository paymentRepository;

    @Value("${toss.secret-key}")
    private String widgetSecretKey;

    @Override
    public Map<String, Object> confirmPayment(PaymentConfirmRequest request) {

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("paymentKey", request.paymentKey());
        requestData.put("orderId", request.orderId());
        requestData.put("amount", request.amount());


        // Toss 결제 승인 API 호출
        Map<String, Object> response = restClient.post()
                .uri("https://api.tosspayments.com/v1/payments/confirm")
                .headers(headers -> headers.setBasicAuth(widgetSecretKey, ""))
                .body(requestData)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {
                });

        // 결제 승인 정보 DB 저장
        Payment payment = Payment.builder()


        )


    }
}
