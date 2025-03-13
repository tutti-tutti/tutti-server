package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.MemberAgreementMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAgreementMappingRepository extends
        JpaRepository<MemberAgreementMapping, Long> {

}
