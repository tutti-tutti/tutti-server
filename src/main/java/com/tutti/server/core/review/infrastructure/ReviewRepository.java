package com.tutti.server.core.review.infrastructure;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    default Review findOne(Long id) {
        return findById(id)
            .orElseThrow(() -> new DomainException(ExceptionType.REVIEW_NOT_FOUND));
    }

    Page<Review> findByProductId(Long productId, Pageable pageable);

    Page<Review> findByProductIdAndIdLessThan(Long productId, Long id, Pageable pageable);

    Page<Review> findByProductIdAndRatingLessThanEqual(Long productId, Long rating,
        Pageable pageable);

//    Page<Review> findByProductIdAndLikeCountLessThanEqual(Long productId, Long likeCount,
//        Pageable pageable);
}
