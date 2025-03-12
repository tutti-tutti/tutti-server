//package com.tutti.server.core.payment.api;
//
//import com.tutti.server.core.payment.application.PaymentService;
//import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequestMapping("api/v1/payments")
//@RequiredArgsConstructor
//public class PaymentsController {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private final PaymentService paymentService;
//
//    @PostMapping("/confirm")
//    public ResponseEntity<Map<String, Object>> confirmPayment(
//            @RequestBody PaymentConfirmRequest request) {
//
//        Map<String, Object> response = paymentService.confirmPayment(request);
//        return ResponseEntity.ok(response);
//
//
//    }
//
//}
