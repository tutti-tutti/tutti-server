package com.tutti.server.core.member.infrastructure;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.ViewedProduct;
import com.tutti.server.core.product.domain.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewedProductRepository extends JpaRepository<ViewedProduct, Long> {

    Optional<ViewedProduct> findByMemberAndProductAndDeleteStatusFalse(Member member,
            Product product);

    Optional<ViewedProduct> findByMemberAndProductIdAndDeleteStatusFalse(Member member,
            Long productId);

    Page<ViewedProduct> findByMemberAndDeleteStatusFalseOrderByViewedAtDesc(Member member,
            Pageable pageable);
}
