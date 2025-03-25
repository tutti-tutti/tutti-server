package com.tutti.server.core.member.application;

import com.tutti.server.core.member.payload.MemberResponse;
import com.tutti.server.core.member.payload.UpdateMemberRequest;

public interface MypageServiceSpec {

    MemberResponse getMyPageInfo(String email);

    void updateMyPageInfo(String email, UpdateMemberRequest request);
}
