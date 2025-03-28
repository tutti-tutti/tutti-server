package com.tutti.server.core.member.application;

import com.tutti.server.core.member.payload.LoginRequest;
import com.tutti.server.core.member.payload.SocialLoginRequest;
import java.util.Map;

public interface AuthServiceSpec {

    Map<String, String> login(LoginRequest request);

    Map<String, String> updateAccessToken(String refreshToken);

    Map<String, String> socialLogin(SocialLoginRequest request);

    void withdrawMember(String refreshToken, String password);

    void withdrawSocialMember(String refreshToken);
}