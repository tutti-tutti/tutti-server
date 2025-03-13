package com.tutti.server.core.payment.api;

import com.tutti.server.core.payment.application.PaymentConfirmService;
import com.tutti.server.core.payment.application.PaymentService;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.PaymentConfirmResponse;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentApi {

    private final PaymentService paymentService;
    private final PaymentConfirmService paymentConfirmService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping("/request")
    public PaymentResponse requestPayment(
            @Valid @RequestBody PaymentRequest request) {

        return paymentService.requestPayment(request);
    }

    @PostMapping("/confirm")
    public ResponseEntity<PaymentConfirmResponse> confirmPayment(
            @Valid @RequestBody PaymentConfirmRequest request) {

        PaymentConfirmResponse response = paymentConfirmService.confirmPayment(request);
        return ResponseEntity.ok(response);
    }
}
