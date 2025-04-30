package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    Optional<ProductLike> findByProductAndMember(Product product, Member member);

    void deleteByProductAndMember(Product product, Member member);

    boolean existsByProductAndMember(Product product, Member member);
}

