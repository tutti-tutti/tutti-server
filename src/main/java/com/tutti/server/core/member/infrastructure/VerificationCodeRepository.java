package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.VerificationCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByEmail(String email);

    void deleteByEmail(String email); // 중복 방지를 위한 메서드 추가
}

