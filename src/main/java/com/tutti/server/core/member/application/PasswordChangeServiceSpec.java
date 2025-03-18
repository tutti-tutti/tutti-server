package com.tutti.server.core.member.application;

import com.tutti.server.core.member.payload.PasswordChangeRequest;

public interface PasswordChangeServiceSpec {

    void changePassword(String email, PasswordChangeRequest request);
}
