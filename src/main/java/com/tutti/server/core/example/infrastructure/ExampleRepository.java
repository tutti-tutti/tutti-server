package com.tutti.server.core.example.infrastructure;

import com.tutti.server.core.example.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<Example, Long> {

}
