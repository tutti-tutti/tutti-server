package com.tutti.server.core.order.infrastructure;

import com.tutti.server.core.order.domain.OrderHistory;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    default OrderHistory findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_HISTORY_NOT_FOUND));
    }

}
