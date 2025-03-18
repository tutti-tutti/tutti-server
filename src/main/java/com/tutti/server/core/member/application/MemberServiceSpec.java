package com.tutti.server.core.member.application;

import com.tutti.server.core.member.payload.SignupRequest;

public interface MemberServiceSpec {

    void signup(SignupRequest request);
}
