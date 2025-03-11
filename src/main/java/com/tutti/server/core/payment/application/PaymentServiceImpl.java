package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.infrastructure.PaymentMethodRepository;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.PaymentConfirmResponse;
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

    private final RestClient restClient;
    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Value("${toss.secret-key}")
    private String widgetSecretKey;

    @Override
    @Transactional
    public Map<String, Object> confirmPayment(PaymentConfirmRequest request) {

        Map<String, Object> requestData = Map.of(
                "paymentKey", request.paymentKey(),
                "orderId", request.orderId(),
                "amount", request.amount()
        );

        // Toss 결제 승인 API 호출
        Map<String, Object> response = restClient.post()
                .uri("https://api.tosspayments.com/v1/payments/confirm")
                .headers(headers -> headers.setBasicAuth(widgetSecretKey, ""))
                .body(requestData)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        Payment payment = paymentRepository.findByTossPaymentKey(request.paymentKey())
                .orElseThrow(() -> new IllegalStateException("해당 결제에 대한 정보가 없습니다."));

        PaymentConfirmResponse paymentResponse = PaymentConfirmResponse.from(response);

        payment.updatePaymentInfo(
                request.paymentKey(),
                paymentResponse.paymentStatus(),
                paymentMethodRepository.findByMethodType(paymentResponse.paymentMethodType())
                        .orElseThrow(() -> new IllegalStateException("해당 결제 수단이 존재하지 않습니다."))
        );
        return response;
    }
}
