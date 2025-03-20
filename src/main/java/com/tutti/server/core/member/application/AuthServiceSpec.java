package com.tutti.server.core.member.application;

import com.tutti.server.core.member.payload.LoginRequest;
import java.util.Map;

public interface AuthServiceSpec {

    Map<String, String> login(LoginRequest request);

    Map<String, String> updateAccessToken(String refreshToken);
}