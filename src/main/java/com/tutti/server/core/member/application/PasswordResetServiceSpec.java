package com.tutti.server.core.member.application;

public interface PasswordResetServiceSpec {

    void resetPassword(String email, String newPassword);
}
