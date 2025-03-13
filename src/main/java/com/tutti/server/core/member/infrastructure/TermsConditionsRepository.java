package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.TermsConditions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsConditionsRepository extends JpaRepository<TermsConditions, Long> {

}
