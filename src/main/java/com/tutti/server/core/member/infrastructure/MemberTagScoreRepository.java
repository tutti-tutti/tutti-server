package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.MemberTagScore;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTagScoreRepository extends JpaRepository<MemberTagScore, Long> {

    Optional<MemberTagScore> findByMemberIdAndTagId(Long memberId, Long tagId);

    List<MemberTagScore> findAllByMemberId(Long memberId);
}
