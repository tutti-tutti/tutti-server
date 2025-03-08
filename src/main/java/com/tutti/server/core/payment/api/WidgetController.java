package com.tutti.server.core.payment.api;

import com.tutti.server.core.payment.application.PaymentService;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;


@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class WidgetController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${toss.secret-key}")
    private String widgetSecretKey;

    private final RestClient restClient;
    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<Map<String, Object>> confirmPayment(@RequestBody PaymentConfirmRequest request) {

        Map<String, Object> response = paymentService.confirmPayment(request);
        return ResponseEntity.ok(response);


    }

}
