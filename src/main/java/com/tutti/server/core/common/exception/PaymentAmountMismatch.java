package com.tutti.server.core.common.exception;

public class PaymentAmountMismatch extends CustomException {

    public PaymentAmountMismatch() {
        super(ErrorCode.PAYMENT_AMOUNT_MISMATCH);
    }

}
