package com.tutti.server.core.payment.api;

import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@Tag(name = "결제 API", description = "결제 요청을 처리하는 API")
public interface PaymentApiSpec {

    @Operation(summary = "결제 요청", description = "사용자가 결제를 요청하는 API")
    PaymentResponse requestPayment(PaymentRequest request);

    @Operation(summary = "결제 승인", description = "사용자가 결제 승인을 요청하는 API")
    ResponseEntity<Map<String, Object>> confirmPayment(PaymentConfirmRequest request);

}
