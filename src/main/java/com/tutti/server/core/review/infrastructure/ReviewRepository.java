package com.tutti.server.core.review.infrastructure;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    default Review findOne(Long id) {
        return findById(id)
            .orElseThrow(() -> new DomainException(ExceptionType.FAQ_NOT_FOUND));
    }

    List<Review> findByProductId(Long productId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.productId = :productId AND r.id < :cursor ORDER BY r.createdAt DESC")
    List<Review> findReviewsByProductIdAndCursor(@Param("productId") Long productId,
        @Param("cursor") Long cursor, Pageable pageable);

}
