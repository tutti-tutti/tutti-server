package com.tutti.server.core.member.application;

public interface EmailVerificationServiceSpec {

    void sendVerificationEmail(String email);

    void verifyEmail(String email, String code);
}
