package com.tutti.server.core.payment.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentCancelApi {

    private final PaymentCancelService paymentCancelService;


    @GetMapping("/cancel/{orderId}")
    public void getPaymentCancel(@PathVariable Long orderId){

        paymentCancelService.
    }

}
