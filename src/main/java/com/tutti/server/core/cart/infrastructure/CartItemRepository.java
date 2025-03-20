package com.tutti.server.core.cart.infrastructure;

import com.tutti.server.core.cart.domain.CartItem;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    default CartItem findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.CART_ITEM_NOT_FOUND));
    }

    Optional<CartItem> findByMemberIdAndIdAndDeleteStatusFalse(Long memberId, Long cartItemId);

    Optional<CartItem> findByMemberIdAndProductItemIdAndDeleteStatusFalse(Long memberId,
            Long productItemId);

    List<CartItem> findAllByMemberIdAndDeleteStatusFalse(Long memberId);
}
