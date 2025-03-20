package com.tutti.server.core.member.application;

import com.tutti.server.core.member.payload.PasswordChangeRequest;

public interface PasswordChangeServiceSpec {

    void changePassword(long memberId, PasswordChangeRequest request);
}
