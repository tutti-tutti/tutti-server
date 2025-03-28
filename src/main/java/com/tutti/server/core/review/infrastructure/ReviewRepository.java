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

    // ===================== ReviewMyListService =====================

    // 사용자가 작성한 리뷰 목록 (초기 조회)
    @Query("""
                SELECT r 
                FROM Review r 
                WHERE r.member.id = :memberId 
                ORDER BY r.id DESC
            """)
    List<Review> findFirstMyReviews(
            @Param("memberId") Long memberId,
            Pageable pageable
    );

    // 사용자가 작성한 리뷰 목록 (커서 기반 조회)
    @Query("""
                SELECT r 
                FROM Review r 
                WHERE r.member.id = :memberId 
                  AND r.id < :cursor
                ORDER BY r.id DESC
            """)
    List<Review> findNextMyReviews(
            @Param("memberId") Long memberId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    // ===================== ReviewListService =====================
    // Reviews -> Product_items -> Products 순으로 조인
    // 최신순 초기 페이지 (복합 커서)
    @Query(value = """
                SELECT r.* 
                FROM reviews r
                JOIN product_items pi ON r.product_item_id = pi.id
                JOIN products p ON pi.product_id = p.id
                WHERE p.id = :productId
                ORDER BY r.created_at DESC
                LIMIT :limit
            """, nativeQuery = true)
    List<Review> findFirstReviewsByProductNative(
            @Param("productId") Long productId,
            @Param("limit") int limit
    );

    // 최신순 다음 페이지 (복합 커서)
    @Query(value = """
                SELECT r.* 
                FROM reviews r
                JOIN product_items pi ON r.product_item_id = pi.id
                JOIN products p ON pi.product_id = p.id
                WHERE p.id = :productId
                  AND r.id < :cursor
                ORDER BY r.created_at DESC
                LIMIT :limit
            """, nativeQuery = true)
    List<Review> findNextReviewsByProductNative(
            @Param("productId") Long productId,
            @Param("cursor") Long cursor,
            @Param("limit") int limit
    );

    // 별점 초기 페이지 (복합 커서)
    @Query(value = """
            SELECT r.* FROM reviews r
                                JOIN product_items pi ON r.product_item_id = pi.id
                                JOIN products p ON pi.product_id = p.id
            WHERE p.id = ?
            ORDER BY r.rating DESC, r.id DESC
            LIMIT ?;
            """, nativeQuery = true)
    List<Review> findFirstReviewsByProductOrderByRatingDescNative(
            @Param("productId") Long productId,
            @Param("limit") int limit
    );

    // 별점 다음 페이지 (복합 커서)
    @Query(value = """
               SELECT r.* FROM reviews r
                                           JOIN product_items pi ON r.product_item_id = pi.id
                                           JOIN products p ON pi.product_id = p.id
                       WHERE p.id = ?
                         AND (
                           r.rating < ? OR
                           (r.rating = ? AND r.id < ?)
                           )
            
                       ORDER BY r.rating DESC, r.id DESC
                       LIMIT ?;
            """, nativeQuery = true)
    List<Review> findNextReviewsByProductOrderByRatingDescNative(
            @Param("productId") Long productId,
            @Param("cursorRating") Float cursorRating,
            @Param("cursorRatingEq") Float cursorRatingEq, // rating = ?
            @Param("cursorId") Long cursorId,
            @Param("limit") int limit
    );

    // 도움이 되었어요 초기 페이지 (복합 커서)
    @Query(value = """
            SELECT r.* FROM reviews r
                                JOIN product_items pi ON r.product_item_id = pi.id
                                JOIN products p ON pi.product_id = p.id
            WHERE p.id = ?
            ORDER BY r.like_count DESC, r.id DESC
            LIMIT ?;
            """, nativeQuery = true)
    List<Review> findFirstReviewsByProductOrderByLikeDescNative(
            @Param("productId") Long productId,
            @Param("limit") int limit
    );

    // 도움이 되었어요 다음 페이지 (복합 커서)
    @Query(value = """
            SELECT r.* FROM reviews r
                                JOIN product_items pi ON r.product_item_id = pi.id
                                JOIN products p ON pi.product_id = p.id
            WHERE p.id = ?
              AND (
                r.like_count < ? OR
                (r.like_count = ? AND r.id < ?)
                )
            
            ORDER BY r.like_count DESC, r.id DESC
            LIMIT ?;
            """, nativeQuery = true)
    List<Review> findNextReviewsByProductOrderByLikeDescNative(
            @Param("productId") Long productId,
            @Param("cursorLikeCount") Integer cursorLikeCount,
            @Param("cursorLikeCountEq") Integer cursorLikeCountEq, // 중복 바인딩
            @Param("cursorId") Long cursorId,
            @Param("limit") int limit
    );

    // 평균 평점
    @Query("""
                SELECT AVG(r.rating) 
                FROM Review r 
                WHERE r.productItem.product.id = :productId
            """)
    Double findAverageRatingByProductId(Long productId);

    // 별점별 개수
    @Query(value = """
                SELECT FLOOR(r.rating) AS star, COUNT(*) AS count
                FROM reviews r
                JOIN product_items pi ON r.product_item_id = pi.id
                JOIN products p ON pi.product_id = p.id
                WHERE p.id = :productId
                GROUP BY FLOOR(r.rating)
                ORDER BY star DESC
            """, nativeQuery = true)
    List<Object[]> countStarGroupBy(@Param("productId") Long productId);

    // 긍정 리뷰 비율 (소수점 1자리 반올림)
    @Query(value = """
                SELECT 
                    ROUND(
                        (SELECT COUNT(*) 
                         FROM reviews r 
                         JOIN product_items pi ON r.product_item_id = pi.id
                         WHERE pi.product_id = :productId AND r.sentiment = 'positive') * 100.0
                        / NULLIF((
                            SELECT COUNT(*) 
                            FROM reviews r 
                            JOIN product_items pi ON r.product_item_id = pi.id
                            WHERE pi.product_id = :productId
                        ), 0), 1
                    )
            """, nativeQuery = true)
    Double getPositiveSentimentRate(@Param("productId") Long productId);
}
