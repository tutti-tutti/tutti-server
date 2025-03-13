package com.tutti.server.core.support.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final ExceptionType exceptionType;

    private final Object data;

    public DomainException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.data = null;
    }

    public DomainException(ExceptionType exceptionType, Object data) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.data = data;
    }

}
