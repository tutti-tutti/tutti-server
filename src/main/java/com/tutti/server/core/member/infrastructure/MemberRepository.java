package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
