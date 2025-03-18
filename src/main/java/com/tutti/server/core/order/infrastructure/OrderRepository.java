package com.tutti.server.core.order.infrastructure;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    default Order findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));
    }

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findAllByMemberEmailAndDeleteStatusFalse(String email);
}
