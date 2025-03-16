package com.tutti.server.core.review.infrastructure;

import com.tutti.server.core.review.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    ReviewLike findByReviewIdAndMemberId(Long reviewId, Long memberId);
}

