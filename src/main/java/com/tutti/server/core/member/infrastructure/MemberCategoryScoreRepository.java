package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.MemberCategoryScore;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCategoryScoreRepository extends JpaRepository<MemberCategoryScore, Long> {

    Optional<MemberCategoryScore> findByMemberIdAndCategoryId(Long memberId, Long categoryId);
}

