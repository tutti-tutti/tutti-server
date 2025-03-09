package com.tutti.server.core.member.repository;

import com.tutti.server.core.member.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByEmail(String email);

    void deleteByEmail(String email); // 중복 방지를 위한 메서드 추가
}