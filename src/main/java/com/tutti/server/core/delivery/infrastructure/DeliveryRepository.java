package com.tutti.server.core.delivery.infrastructure;

import com.tutti.server.core.delivery.domain.Delivery;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    default Delivery findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.DELIVERY_NOT_FOUND));
    }

    Optional<Delivery> findByOrderId(Long orderId);
}
