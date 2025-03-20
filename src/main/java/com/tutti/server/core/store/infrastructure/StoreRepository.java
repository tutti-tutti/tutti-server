package com.tutti.server.core.store.infrastructure;

import com.tutti.server.core.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
