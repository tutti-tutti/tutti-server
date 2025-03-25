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

    // 최신 리뷰 20개 조회 (초기 조회)
    @Query("""
                SELECT r FROM Review r 
                WHERE r.productItem.product.id = :productId
                ORDER BY r.createdAt DESC
            """)
    List<Review> findFirstReviewsByProduct(
            @Param("productId") Long productId,
            Pageable pageable
    );

    // 특정 상품의 다음 리뷰 조회 (무한 스크롤 방식)
    @Query("""
                SELECT r FROM Review r 
                WHERE r.productItem.product.id = :productId
                AND r.id < :cursor
                ORDER BY r.createdAt DESC
            """)
    List<Review> findNextReviewsByProduct(
            @Param("productId") Long productId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    // 사용자가 작성한 리뷰 목록 (초기 조회)
    @Query("""
                SELECT r FROM Review r 
                WHERE r.nickname = :nickname 
                ORDER BY r.id DESC
            """)
    List<Review> findFirstMyReviews(
            @Param("nickname") String nickname,
            Pageable pageable
    );

    // 사용자가 작성한 리뷰 목록 (커서 기반 조회)
    @Query("""
                SELECT r FROM Review r 
                WHERE r.nickname = :nickname 
                AND r.id < :cursor
                ORDER BY r.id DESC
            """)
    List<Review> findNextMyReviews(
            @Param("nickname") String nickname,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    // 특정 상품의 평점 높은 순 정렬 (초기 20개)
    @Query("""
                SELECT r FROM Review r 
                WHERE r.productItem.product.id = :productId 
                ORDER BY r.rating DESC
            """)
    List<Review> findFirstReviewsByProductOrderByRatingDesc(
            @Param("productId") Long productId,
            Pageable pageable
    );

    // 특정 상품의 평점 높은 순 정렬 (커서 기반)
    @Query("""
                SELECT r FROM Review r 
                WHERE r.productItem.product.id = :productId 
                AND r.id < :cursor
                ORDER BY r.rating DESC
            """)
    List<Review> findNextReviewsByProductOrderByRatingDesc(
            @Param("productId") Long productId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    // 특정 상품의 좋아요 많은 순 정렬 (초기 20개)
    @Query("""
                SELECT r FROM Review r 
                WHERE r.productItem.product.id = :productId 
                ORDER BY r.likeCount DESC
            """)
    List<Review> findFirstReviewsByProductOrderByLikeDesc(
            @Param("productId") Long productId,
            Pageable pageable
    );

    // 특정 상품의 좋아요 많은 순 정렬 (커서 기반)
    @Query("""
                SELECT r FROM Review r 
                WHERE r.productItem.product.id = :productId 
                AND r.id < :cursor
                ORDER BY r.likeCount DESC
            """)
    List<Review> findNextReviewsByProductOrderByLikeDesc(
            @Param("productId") Long productId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    // 리뷰의 평점을 합산하여 평균을 계산
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productItem.product.id = :productId")
    Double findAverageRatingByProductId(Long productId);

    // 리뷰의 별점별 갯수
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
}
