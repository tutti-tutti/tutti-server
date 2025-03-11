package com.tutti.server.global.exception;

public class PaymentAlreadyCompletedException extends CustomException {

    public PaymentAlreadyCompletedException() {
        super(ErrorCode.PAYMENT_ALREADY_COMPLETED);
    }
}
