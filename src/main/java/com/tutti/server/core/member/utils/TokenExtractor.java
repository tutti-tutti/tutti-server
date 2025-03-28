package com.tutti.server.core.member.utils;

import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;

public class TokenExtractor {

    public static String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new DomainException(ExceptionType.MISSING_AUTH_HEADER);
    }
}
