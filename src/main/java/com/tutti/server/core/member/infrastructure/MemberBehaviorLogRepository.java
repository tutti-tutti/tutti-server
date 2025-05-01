package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.MemberBehaviorLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBehaviorLogRepository extends JpaRepository<MemberBehaviorLog, Long> {

    List<MemberBehaviorLog> findAllByMemberId(Long memberId);
}