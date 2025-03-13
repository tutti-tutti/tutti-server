package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentMethodRepository;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
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
public class PaymentConfirmServiceImpl implements PaymentConfirmService {

    private final RestClient restClient;
    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Value("${toss.secret-key}")
    private String widgetSecretKey;

    @Override
    @Transactional
    public Map<String, Object> confirmPayment(PaymentConfirmRequest request) {

        Map<String, Object> requestBody = Map.of(
                "paymentKey", request.paymentKey(),
                "orderId", request.orderId(),
                "amount", request.amount()
        );

        Payment payment = paymentRepository.findByTossOrderId(request.orderId())
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));

        Map<String, Object> response = restClient.post()
                .uri("https://api.tosspayments.com/v1/payments/confirm")
                .headers(headers -> headers.setBasicAuth(widgetSecretKey, ""))
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        // Map에서 데이터 추출 (캐스팅 필요)

        String paymentStatusStr = (String) response.get("status");
        String paymentMethodTypeStr = (String) response.get("method");
        String approvedAtStr = (String) response.get("approvedAt");
        Integer totalAmount = (Integer) response.get("totalAmount");

//        PaymentMethod paymentMethod = paymentMethodRepository.findByMethodType(
//                        PaymentMethodType.fromString(paymentMethodTypeStr)) // 한글 → Enum 변환
//                .orElseThrow(() -> new IllegalStateException("해당 결제 수단이 존재하지 않습니다."));

//        PaymentMethodType paymentMethodType = PaymentMethodType.fromString(paymentMethodTypeStr);

        int amount = (totalAmount != null) ? totalAmount : 0;

//         결제 상태 업데이트
        payment.updatePayment(
                request.paymentKey(), // 결제 키 저장
                PaymentStatus.valueOf(paymentStatusStr), // Enum 변환
//                paymentMethodType, // 변환된 PaymentMethod 사용
                LocalDateTime.parse(approvedAtStr), // 승인 시간 저장
                amount // 변환된 int 값 사용
        );

        paymentRepository.save(payment);

        return response; // ✅ JSON 응답 그대로 반환
    }
}

