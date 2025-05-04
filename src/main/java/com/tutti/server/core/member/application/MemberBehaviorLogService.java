package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.BehaviorType;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.product.domain.Product;

public interface MemberBehaviorLogService {

    void log(Member member, Product product, BehaviorType behaviorType);

}
