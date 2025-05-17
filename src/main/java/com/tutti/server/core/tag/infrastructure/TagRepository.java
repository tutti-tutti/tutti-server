package com.tutti.server.core.tag.infrastructure;

import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import com.tutti.server.core.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    default Tag findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_TAG_NOT_FOUND));
    }
}
