package com.tutti.server.global.exception;

public class PaymentAmountMismatch extends CustomException {

    public PaymentAmountMismatch() {
        super(ErrorCode.PAYMENT_AMOUNT_MISMATCH);
    }

}
