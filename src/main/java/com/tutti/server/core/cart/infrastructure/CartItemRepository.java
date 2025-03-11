package com.tutti.server.core.cart.infrastructure;

import com.tutti.server.core.cart.domain.CartItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByIdAndMemberIdAndDeleteStatusFalse(Long cartItemId, Long memberId);
    
    Optional<CartItem> findByMemberIdAndProductItemIdAndDeleteStatusFalse(Long memberId,
            Long productItemId);

    List<CartItem> findAllByMemberIdAndDeleteStatusFalse(Long memberId);
}
