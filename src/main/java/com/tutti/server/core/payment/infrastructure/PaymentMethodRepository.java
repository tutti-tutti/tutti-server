package com.tutti.server.core.payment.infrastructure;

import com.tutti.server.core.payment.domain.PaymentMethod;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    Optional<PaymentMethod> findByMethodType(PaymentMethodType paymentMethodType);

}
