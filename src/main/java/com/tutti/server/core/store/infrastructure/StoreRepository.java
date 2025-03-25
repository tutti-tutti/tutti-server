package com.tutti.server.core.store.infrastructure;

import com.tutti.server.core.store.domain.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findById(Long id);

    Optional<Store> findByName(String name);

}
