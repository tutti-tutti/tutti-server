package com.tutti.server.core.order.infrastructure;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    default Order findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));
    }

    boolean existsByIdAndDeleteStatusFalse(Long orderId);

    @Query("""
                SELECT o FROM Order o
                WHERE o.member.id = :memberId
                  AND o.deleteStatus = false
                  AND (
                       (:cursorCreatedAt IS NULL)
                       OR (o.createdAt < :cursorCreatedAt)
                       OR (o.createdAt = :cursorCreatedAt AND o.id < :cursorId)
                  )
                ORDER BY o.createdAt DESC, o.id DESC
            """)
    List<Order> findByMemberIdWithCursor(
            @Param("memberId") Long memberId,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    Optional<Order> findByIdAndMemberIdAndDeleteStatusFalse(Long orderId, Long memberId);

    Optional<Order> findByOrderSheetNoAndMemberId(String orderSheetNo,
            Long memberId); //  orderSheetNo로 추가
}
