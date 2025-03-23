package com.tutti.server.core.order.infrastructure;

import com.tutti.server.core.order.domain.OrderHistory;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    default OrderHistory findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_HISTORY_NOT_FOUND));
    }

    // 주문 ID로 최신 이력 찾기
    Optional<OrderHistory> findByOrderIdAndLatestVersionTrue(Long orderId);

    // 주문 ID로 모든 이력 중 latestVersion이 true인 것을 false로 업데이트
    @Modifying
    @Query("UPDATE OrderHistory oh SET oh.latestVersion = false "
            + "WHERE oh.order.id = :orderId AND oh.latestVersion = true")
    void updatePreviousVersions(Long orderId);
}
