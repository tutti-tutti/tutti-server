package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentMethodRepository;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
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

        Map<String, Object> response = restClient.post()
                .uri("https://api.tosspayments.com/v1/payments/confirm")
                .headers(headers -> headers.setBasicAuth(widgetSecretKey, ""))
                .body(request)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        // ✅ Map에서 데이터 추출 (캐스팅 필요)
        String paymentStatusStr = (String) response.get("status");
        String paymentMethodTypeStr = (String) response.get("method");

        // ✅ 결제 상태 업데이트
        payment.updatePayment(
                request.paymentKey(),
                PaymentStatus.valueOf(paymentStatusStr), // ✅ Enum 변환
                paymentMethodRepository.findByMethodType(
                                PaymentMethodType.valueOf(paymentMethodTypeStr))
                        .orElseThrow(() -> new IllegalStateException("해당 결제 수단이 존재하지 않습니다."))
        );

        return response; // ✅ JSON 응답 그대로 반환
    }
}

