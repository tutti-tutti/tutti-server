package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.BehaviorType;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.MemberBehaviorLog;
import com.tutti.server.core.member.infrastructure.MemberBehaviorLogRepository;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberBehaviorLogServiceImpl implements MemberBehaviorLogService {

    private final MemberBehaviorLogRepository behaviorLogRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    public void log(Member member, Product product, BehaviorType behaviorType) {
        MemberBehaviorLog log = MemberBehaviorLog.builder()
                .member(member)
                .product(product)
                .behaviorType(behaviorType)
                .build();

        behaviorLogRepository.save(log);
    }
}
