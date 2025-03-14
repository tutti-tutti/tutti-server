package com.tutti.server.core.payment.api;

import com.tutti.server.core.payment.application.PaymentConfirmService;
import com.tutti.server.core.payment.application.PaymentService;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:5500") // 프론트엔드 주소
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
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
    public ResponseEntity<Map<String, Object>> confirmPayment(
            @Valid @RequestBody PaymentConfirmRequest request) {

        Map<String, Object> response = paymentConfirmService.confirmPayment(request);
        return ResponseEntity.ok(response);
    }
}
