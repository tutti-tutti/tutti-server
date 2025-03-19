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
            .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_REVIEW_NOT_FOUND));
    }

    @Query("""
            SELECT r FROM Review r 
            WHERE r.nickname = :nickname 
            ORDER BY r.id DESC
        """)
    List<Review> findFirstMyReviews(@Param("nickname") String nickname, Pageable pageable);

    @Query("""
            SELECT r FROM Review r 
            WHERE r.nickname = :nickname 
            AND r.id < :cursor
            ORDER BY r.id DESC
        """)
    List<Review> findNextMyReviews(@Param("nickname") String nickname, @Param("cursor") Long cursor,
        Pageable pageable);

    List<Review> findFirstReviewsByProductId(Long productId, Pageable pageable);

    @Query("""
            SELECT r FROM Review r 
            WHERE r.productId = :productId 
            AND (:cursor IS NULL OR r.id < :cursor)
            ORDER BY r.createdAt DESC
        """)
    List<Review> findReviewsByProductIdAndCursor(
        @Param("productId") Long productId,
        @Param("cursor") Long cursor,
        Pageable pageable
    );

}
