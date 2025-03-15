package com.tutti.server.core.order.infrastructure;

import com.tutti.server.core.order.domain.OrderItem;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    default OrderItem findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_ITEM_NOT_FOUND));
    }

    List<OrderItem> findByOrderId(Long orderId);

}
