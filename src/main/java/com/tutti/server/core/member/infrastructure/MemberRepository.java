package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));
    }

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);
}
