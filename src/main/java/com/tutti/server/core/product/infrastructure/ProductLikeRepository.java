package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.domain.ProductLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    Optional<ProductLike> findByProductIdAndMemberId(Long productId, Long memberId);

    void deleteByProductIdAndMemberId(Long productId, Long memberId);

    boolean existsByProductIdAndMemberId(Long productId, Long memberId);
}

