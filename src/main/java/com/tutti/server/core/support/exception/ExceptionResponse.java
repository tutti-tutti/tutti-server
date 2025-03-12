package com.tutti.server.core.support.exception;

public record ExceptionResponse(

        ExceptionCode exceptionCode,
        String message,
        Object data
) {

    public static ExceptionResponse from(ExceptionType exceptionType, Object data) {
        return new ExceptionResponse(exceptionType.getCode(), exceptionType.getMessage(), data);
    }

    public static ExceptionResponse from(ExceptionType exceptionType) {
        return new ExceptionResponse(exceptionType.getCode(), exceptionType.getMessage(), null);
    }
}
