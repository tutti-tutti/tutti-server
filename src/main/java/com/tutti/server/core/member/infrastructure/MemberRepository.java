package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    Optional<String> findNicknameById(Long memberId); // 리뷰 닉네임 조회
}
