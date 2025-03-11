package com.tutti.server.core.common.exception;

public class OrderNotFoundException extends CustomException {

    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUND);
    }
}
