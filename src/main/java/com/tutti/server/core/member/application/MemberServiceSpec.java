package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.payload.SignupRequest;
import com.tutti.server.core.member.payload.TermsAgreementRequest;
import java.util.List;
import java.util.Map;

public interface MemberServiceSpec {

    void signup(SignupRequest request);

    Member socialSignup(String email, String provider, String accessToken);

    Map<String, String> socialLogin(String provider, String accessToken);

    void completeSocialSignup(Long userId, List<TermsAgreementRequest> termsAgreement);
}
