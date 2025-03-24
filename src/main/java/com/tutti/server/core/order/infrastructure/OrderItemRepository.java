package com.tutti.server.core.order.infrastructure;

import com.tutti.server.core.order.domain.OrderItem;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    default OrderItem findOne(Long id) {
        return findById(id)
            .orElseThrow(() -> new DomainException(ExceptionType.ORDER_ITEM_NOT_FOUND));
    }

    List<OrderItem> findAllByOrderId(Long orderId);

    @Query("SELECT COUNT(oi) > 0 FROM OrderItem oi " +
        "WHERE oi.order.member.id = :memberId AND oi.productItem.id = :productItemId")
    boolean existsByOrderMemberIdAndProductItemId(Long memberId, Long productItemId);

}
