package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.TossPaymentsCancelRequest;
import com.tutti.server.core.payment.payload.TossPaymentsCancelResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
@RequiredArgsConstructor
public class TossPaymentService {

    private final RestClient restClient;

    @Value("${toss.secret-key}")
    private String widgetSecretKey;

    @Value("${toss.cancel-api}")
    private String tossPaymentsCancelUrl;

    @Value("${toss.confirm-api}")
    private String tossPaymentsConfirmUrl;

    //Toss API 요청 헤더 생성.
    public HttpHeaders createTossApiHeaders() {
        String secretKeyBase64 = Base64.getEncoder()
                .encodeToString((widgetSecretKey + ":").getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(secretKeyBase64);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Idempotency-Key", UUID.randomUUID().toString()); //멱등성 키 포함하여 중복 예방.(토스 가이드)

        return headers;
    }

    // Toss API 결제 승인 요청
    public Map<String, Object> confirmPayment(PaymentConfirmRequest request) {
        HttpHeaders headers = createTossApiHeaders();
        Map<String, Object> requestBody = buildRequestBody(request);

        try {
            return restClient.method(HttpMethod.POST)
                    .uri(tossPaymentsConfirmUrl)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(requestBody)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

        } catch (RestClientResponseException e) {
            throw new RuntimeException("Toss 결제 승인 요청 실패: " + e.getMessage(), e);
        }
    }

    // Toss API 결제 승인 요청 바디 생성
    private Map<String, Object> buildRequestBody(PaymentConfirmRequest request) {
        return Map.of(
                "paymentKey", request.paymentKey(),
                "orderId", request.orderId(),
                "amount", request.amount()
        );
    }

    //Toss api 결제 취소 요청.
    public TossPaymentsCancelResponse cancelPayment(Payment payment, String cancelReason) {
        String cancelUrl = tossPaymentsCancelUrl + "/" + payment.getTossPaymentKey() + "/cancel";
        HttpHeaders headers = createTossApiHeaders();
        TossPaymentsCancelRequest tossRequest = new TossPaymentsCancelRequest(cancelReason);

        try {
            Map<String, Object> response = restClient.method(HttpMethod.POST)
                    .uri(cancelUrl)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(tossRequest)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (response == null) {
                throw new DomainException(ExceptionType.TOSS_NOT_RESPONSE);
            }
            return TossPaymentsCancelResponse.fromResponse(response);

        } catch (RestClientResponseException e) {
            throw new DomainException(ExceptionType.TOSS_ERROR);
        }
    }
}
