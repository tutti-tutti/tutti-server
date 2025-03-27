package com.tutti.server.core.store.infrastructure;

import com.tutti.server.core.store.domain.Store;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    default Store findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.STORE_NOT_FOUND));
    }

    Optional<Store> findByName(String name);

}
