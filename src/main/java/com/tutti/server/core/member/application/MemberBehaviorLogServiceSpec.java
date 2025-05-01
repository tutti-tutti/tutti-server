package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.BehaviorType;

public interface MemberBehaviorLogServiceSpec {

    void log(Long memberId, Long productId, BehaviorType behaviorType);

    ;
}
