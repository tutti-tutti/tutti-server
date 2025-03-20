package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsConditionsRepository extends JpaRepository<TermsConditions, Long> {

    default TermsConditions findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.TERMS_NOT_FOUND));
    }
}
