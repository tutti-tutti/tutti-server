package com.tutti.server.core.common.exception;

public class PaymentAlreadyCompletedException extends CustomException {

    public PaymentAlreadyCompletedException() {
        super(ErrorCode.PAYMENT_ALREADY_COMPLETED);
    }
}
