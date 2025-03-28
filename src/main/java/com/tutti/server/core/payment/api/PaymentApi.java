package com.tutti.server.core.payment.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.order.application.OrderService;
import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.payment.application.PaymentCancelService;
import com.tutti.server.core.payment.application.PaymentService;
import com.tutti.server.core.payment.application.PaymentViewService;
import com.tutti.server.core.payment.payload.request.PaymentCancelRequest;
import com.tutti.server.core.payment.payload.request.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.request.PaymentRequest;
import com.tutti.server.core.payment.payload.response.PaymentResponse;
import com.tutti.server.core.payment.payload.response.PaymentViewResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@SecurityRequirement(name = "Bearer Authentication")
public class PaymentApi implements PaymentApiSpec {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final PaymentViewService paymentViewService;
    private final PaymentCancelService paymentCancelService;

    @PostMapping
    public PaymentResponse requestPayment(
            @Valid @RequestBody OrderCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {
        PaymentRequest paymentRequest = orderService.createOrder(request, user.getMemberId());

        return paymentService.requestPayment(paymentRequest, user.getMemberId());
    }

    @PostMapping("/confirm/success")
    public void confirmPayment(
            @Valid @RequestBody PaymentConfirmRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {

        paymentService.confirmPayment(request, user.getMemberId());
    }

    @PostMapping("/cancel")
    public void cancelPayment(
            @Valid @RequestBody PaymentCancelRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {

        paymentCancelService.paymentCancel(request, user.getMemberId());
    }

    // paymentId로 결제 조회
    @GetMapping("/paymentId/{paymentId}")
    public PaymentViewResponse getPaymentIdViewPayments(@PathVariable Long paymentId,
            @AuthenticationPrincipal CustomUserDetails user) {

        return paymentViewService.viewPaymentByMemberId(paymentId, user.getMemberId());
    }

    // 주문ID로 결제 조회
    @GetMapping("/orderId/{orderId}")
    public PaymentViewResponse getPaymentsViewOrderId(@PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails user) {

        return paymentViewService.viewPaymentByOrderId(orderId, user.getMemberId());
    }

}
