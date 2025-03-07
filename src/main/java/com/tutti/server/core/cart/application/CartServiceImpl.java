package com.tutti.server.core.cart.application;

import com.tutti.server.core.cart.infrastructure.CartItemRepository;
import com.tutti.server.core.cart.payload.request.CartItemRequest;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.support.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ProductItemRepository productItemRepository;

    @Override
    @Transactional
    public void createCartItem(CartItemRequest request) {
        var member = memberRepository.findById(request.memberId())
                .orElseThrow(RuntimeException::new);
        var productItem = productItemRepository.findById(request.productItemId())
                .orElseThrow(RuntimeException::new);

        cartItemRepository.findByMemberIdAndProductItemIdAndDeleteStatusFalse(request.memberId(),
                        request.productItemId())
                // 이미 장바구니에 해당 상품이 있다면 엔티티 교체
                .ifPresentOrElse(BaseEntity::delete,
                        // 장바구니에 상품이 없으면 새로 생성하여 저장
                        () -> cartItemRepository.save(request.toEntity(member, productItem))
                );
    }
}
