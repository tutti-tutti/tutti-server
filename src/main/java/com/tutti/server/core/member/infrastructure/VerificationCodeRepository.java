package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.VerificationCode;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    default VerificationCode findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.INVALID_VERIFICATION_CODE));
    }

    Optional<VerificationCode> findByEmail(String email);

    void deleteByEmail(String email); // 중복 방지를 위한 메서드 추가
}

