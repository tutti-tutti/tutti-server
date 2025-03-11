package com.tutti.server.global.exception;

public record ErrorResponse(String errorCode, int status, String message) {

}
