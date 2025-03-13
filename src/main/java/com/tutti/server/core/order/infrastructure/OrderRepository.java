package com.tutti.server.core.order.infrastructure;

import com.tutti.server.core.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
