package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_NOT_FOUND));
    }

    @Query("SELECT p FROM Product p JOIN ProductCategoryMap pcm ON p = pcm.product " +
            "WHERE pcm.category.id = :categoryId AND pcm.deleteStatus = false")
    List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);

    List<Product> findAllByOrderByCreatedAtDesc();

    @Query("SELECT p FROM Product p WHERE (:cursorId IS NULL OR (p.createdAt < (SELECT p2.createdAt FROM Product p2 WHERE p2.id = :cursorId) OR (p.createdAt = (SELECT p2.createdAt FROM Product p2 WHERE p2.id = :cursorId) AND p.id < :cursorId))) ORDER BY p.createdAt DESC, p.id DESC LIMIT :size")
    List<Product> findProductsByCursorId(@Param("cursorId") Long cursorId,
            @Param("size") int size);

    @Query("SELECT p FROM Product p ORDER BY p.likeCount DESC LIMIT :size")
    List<Product> findTopByOrderByLikeCountDesc(@Param("size") int size);

    @Query("SELECT p FROM Product p WHERE " +
            "(:cursorId IS NULL OR (p.createdAt < (SELECT p2.createdAt FROM Product p2 WHERE p2.id = :cursorId) "
            +
            "OR (p.createdAt = (SELECT p2.createdAt FROM Product p2 WHERE p2.id = :cursorId) AND p.id < :cursorId))) "
            +
            "AND (p.name LIKE CONCAT('%', :searchWord, '%') OR p.description LIKE CONCAT('%', :searchWord, '%')) "
            +
            "AND p.onSales = true " +
            "AND p.deleteStatus = false " +
            "ORDER BY p.createdAt DESC, p.id DESC LIMIT :size")
    List<Product> findProductsBySearchWord(
            @Param("cursorId") Long cursorId,
            @Param("size") int size,
            @Param("searchWord") String searchWord);

    @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', :searchWord, '%') OR p.description LIKE CONCAT('%', :searchWord, '%')")
    List<Product> findProductsBySimpleSearch(@Param("searchWord") String searchWord);

    @Query("""
                SELECT p FROM Product p
                LEFT JOIN FETCH p.productTags pt
                LEFT JOIN FETCH pt.tag
                WHERE p.id = :productId
            """)
    Optional<Product> findWithTagsById(@Param("productId") Long productId);

    List<Product> findTop100ByOrderByCreatedAtDesc();
}
