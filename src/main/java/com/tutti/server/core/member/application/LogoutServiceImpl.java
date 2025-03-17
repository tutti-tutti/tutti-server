package com.tutti.server.core.member.application;

import com.tutti.server.core.member.jwt.JWTUtil;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutServiceSpec {

    private final JWTUtil jwtUtil;

    @Override
    public void logout(String token) {
        if (jwtUtil.isExpired(token)) {
            throw new DomainException(ExceptionType.TOKEN_EXPIRED);
        }
    }
}
