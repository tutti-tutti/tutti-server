package com.tutti.server.core.payment.api;

import com.tutti.server.core.payment.application.PaymentService;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
@Validated
public class PaymentApi {

    private final PaymentService paymentService;

    @PostMapping("/request")
    public ResponseEntity<PaymentResponse> requestPayment(
            @Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.requestPayment(request);
        return ResponseEntity.ok(response);
    }

}
