package com.tutti.server.core.cart.application;

import com.tutti.server.core.cart.infrastructure.CartItemRepository;
import com.tutti.server.core.cart.payload.request.CartItemCreateRequest;
import com.tutti.server.core.cart.payload.response.CartItemsResponse;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.support.entity.BaseEntity;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
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
    @Transactional(readOnly = true)
    public List<CartItemsResponse> getCartItems(Long memberId) {
        return cartItemRepository.findAllByMemberIdAndDeleteStatusFalse(memberId)
                .stream()
                .map(CartItemsResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    // 기존 장바구니 상품이 있는지 확인하고, 없으면 새로 생성하는 메서드
    public void addCartItem(CartItemCreateRequest request) {
        cartItemRepository.findByMemberIdAndProductItemIdAndDeleteStatusFalse(request.memberId(),
                        request.productItemId())
                // 이미 장바구니에 해당 상품이 있다면 수량만 업데이트
                .ifPresentOrElse(item -> item.changeQuantity(request.quantity()),
                        // 없다면 장바구니에 상품을 새로 생성하여 저장
                        () -> createCartItem(request));
    }

    @Override
    @Transactional
    // 장바구니 상품을 엔티티로 저장하는 메서드
    public void createCartItem(CartItemCreateRequest request) {
        var member = memberRepository.findOne(request.memberId());
        var productItem = productItemRepository.findOne(request.productItemId());

        // 장바구니 상품 엔티티를 만들 때, 수량도 받아야 하기 때문에 파라미터로 request 가 필요하다
        cartItemRepository.save(request.toEntity(member, productItem));
    }

    @Override
    @Transactional
    public void removeCartItem(Long cartItemId, Long memberId) {
        cartItemRepository.findByIdAndMemberIdAndDeleteStatusFalse(cartItemId, memberId)
                .ifPresentOrElse(BaseEntity::delete,
                        () -> {
                            throw new DomainException(ExceptionType.UNAUTHORIZED_ERROR);
                        });
    }
}
