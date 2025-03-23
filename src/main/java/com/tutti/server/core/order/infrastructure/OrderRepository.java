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

    boolean existsByIdAndDeleteStatusFalse(Long orderId);

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findAllByMemberIdAndDeleteStatusFalse(Long memberId);

    Optional<Order> findByIdAndMemberIdAndDeleteStatusFalse(Long orderId, Long memberId);

    Optional<Order> findByOrderNumberAndMemberId(String orderNumber,
            Long memberId); //  orderNumber로 추가
}
