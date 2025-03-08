package com.tutti.server.core.payment.payload;

public record PaymentConfirmRequest(String paymentKey, String orderId, int amount) {
}
