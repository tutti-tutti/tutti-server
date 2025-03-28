package com.tutti.server.core.member.application;

import com.tutti.server.core.member.payload.LoginRequest;
import com.tutti.server.core.member.payload.SocialLoginRequest;
import java.util.Map;

public interface AuthServiceSpec {

    Map<String, String> login(LoginRequest request);

    Map<String, String> updateAccessToken(String refreshToken);

    void withdrawMember(String refreshToken, String password);

    Map<String, String> socialLogin(SocialLoginRequest request);
}