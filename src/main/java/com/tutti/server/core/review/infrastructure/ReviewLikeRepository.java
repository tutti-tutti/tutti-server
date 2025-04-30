package com.tutti.server.core.review.infrastructure;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.domain.ReviewLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    Optional<ReviewLike> findByReviewAndMemberId(Review review, Long memberId);

//    boolean existsByReviewAndMemberId(Review review, Long memberId);

    @Query("SELECT rl.review.id FROM ReviewLike rl WHERE rl.memberId = :memberId AND rl.review.id IN :reviewIds")
    List<Long> findLikedReviewIdsByMemberIdAndReviewIds(
            @Param("memberId") Long memberId,
            @Param("reviewIds") List<Long> reviewIds);


}
