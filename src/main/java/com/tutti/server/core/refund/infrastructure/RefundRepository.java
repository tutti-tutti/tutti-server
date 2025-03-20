package com.tutti.server.core.refund.infrastructure;

import com.tutti.server.core.refund.domain.Refund;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    @Query("SELECT r FROM Refund r WHERE r.payment.order.id = :orderId AND r.member.id = :memberId")
    Optional<Refund> findByOrderIdAndMemberId(@Param("orderId") Long orderId,
            @Param("memberId") Long memberId);

    Optional<Refund> findByPaymentOrderId(Long orderId);
}
