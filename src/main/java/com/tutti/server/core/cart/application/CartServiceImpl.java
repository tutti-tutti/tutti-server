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
    // 기존 장바구니 상품이 있는지 확인하고, 없으면 새로 생성하는 메서드
    public void addCartItem(Long memberId, CartItemRequest request) {
        cartItemRepository.findByMemberIdAndProductItemIdAndDeleteStatusFalse(memberId,
                        request.productItemId())
                // 이미 장바구니에 해당 상품이 있다면 삭제(soft delete)
                .ifPresent(BaseEntity::delete);
        // 장바구니에 상품을 새로 생성하여 저장
        createCartItem(memberId, request);
    }

    @Override
    @Transactional
    // 장바구니 상품을 엔티티로 저장하는 메서드
    public void createCartItem(Long memberId, CartItemRequest request) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
        var productItem = productItemRepository.findById(request.productItemId())
                .orElseThrow(RuntimeException::new);

        // 장바구니 상품 엔티티를 만들 때, 수량도 받아야 하기 때문에 파라미터로 request 가 필요하다
        cartItemRepository.save(request.toEntity(member, productItem));
    }
}
