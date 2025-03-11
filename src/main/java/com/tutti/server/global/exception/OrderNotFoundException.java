package com.tutti.server.global.exception;

public class OrderNotFoundException extends CustomException {

    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUND);
    }
}
