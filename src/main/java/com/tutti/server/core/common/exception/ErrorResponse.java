package com.tutti.server.core.common.exception;

public record ErrorResponse(String errorCode, int status, String message) {

}
