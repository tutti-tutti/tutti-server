package com.tutti.server.core.review.infrastructure;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    default Review findOne(Long id) {
        return findById(id)
            .orElseThrow(() -> new DomainException(ExceptionType.FAQ_NOT_FOUND));
    }

    Page<Review> findByProductId(Long productId, Pageable pageable);

    // 수정된 부분: Page<Review>로 반환 타입 변경
    Page<Review> findByProductIdAndIdGreaterThan(Long productId, Long id, Pageable pageable);
}
