package com.tutti.server.core.member.application;

public interface EmailVerificationServiceSpec {

    void sendVerificationEmail(String email, String type);

    void verifyEmail(String email, String code);
}
