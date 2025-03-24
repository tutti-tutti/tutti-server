package com.tutti.server.core.cart.application;

import com.tutti.server.core.cart.domain.CartItem;
import com.tutti.server.core.cart.infrastructure.CartItemRepository;
import com.tutti.server.core.cart.payload.request.CartItemCreateRequest;
import com.tutti.server.core.cart.payload.request.CartItemCreateRequest.CartItemRequest;
import com.tutti.server.core.cart.payload.response.CartItemResponse;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public void addCartItems(CartItemCreateRequest request, Long memberId) {
        // 상품 조회 및 중복 검증
        validateProductItems(request.cartItems());

        for (CartItemCreateRequest.CartItemRequest item : request.cartItems()) {
            cartItemRepository.findByMemberIdAndProductItemIdAndDeleteStatusFalse(memberId,
                            item.productItemId())
                    // 이미 장바구니에 해당 상품이 있다면 수량만 업데이트
                    .ifPresentOrElse(cartItem -> cartItem.changeQuantity(item.quantity()),
                            // 없다면 장바구니에 상품을 새로 생성하여 저장
                            () -> createCartItem(item, memberId));
        }
    }

    @Override
    @Transactional
    // 장바구니에 추가할 상품 아이템들을 검증하고 조회하는 메서드
    public void validateProductItems(List<CartItemRequest> requests) {
        // 상품 아이템 ID 목록
        List<Long> productItemIds = requests.stream()
                .map(CartItemCreateRequest.CartItemRequest::productItemId)
                .toList();

        // 중복 상품 ID 검증
        Set<Long> uniqueIds = new HashSet<>();
        List<Long> duplicateIds = productItemIds.stream()
                .filter(id -> !uniqueIds.add(id))
                .distinct()
                .toList();

        // 중복된 상품 ID가 있으면 예외 발생
        if (!duplicateIds.isEmpty()) {
            throw new DomainException(ExceptionType.DUPLICATE_PRODUCT_ITEMS);
        }

        // DB에서 상품 조회 및 존재 여부 검증
        List<ProductItem> productItems = productItemRepository.findAllById(productItemIds);

        // 요청한 상품 ID 개수와 조회된 상품 개수 비교
        if (productItems.size() != uniqueIds.size()) {
            throw new DomainException(ExceptionType.NON_EXISTENT_PRODUCT_INCLUDE);
        }
    }

    @Override
    @Transactional
    // 장바구니 상품을 엔티티로 저장하는 메서드
    public void createCartItem(CartItemCreateRequest.CartItemRequest request, Long memberId) {
        var member = memberRepository.findOne(memberId);
        var productItem = productItemRepository.findOne(request.productItemId());

        // 장바구니 상품 엔티티를 만들 때, 수량도 받아야 하기 때문에 파라미터로 request 가 필요하다
        cartItemRepository.save(request.toEntity(member, productItem));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemResponse> getCartItems(Long memberId) {
        return cartItemRepository.findAllByMemberIdAndDeleteStatusFalse(memberId)
                .stream()
                .map(CartItemResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public void removeCartItem(Long cartItemId, Long memberId) {
        var cartItem = getCartItem(cartItemId, memberId);
        cartItem.delete();
    }

    @Override
    @Transactional
    public CartItem getCartItem(Long cartItemId, Long memberId) {
        // 1. 장바구니 상품 존재 여부 확인
        boolean exists = cartItemRepository.existsByIdAndDeleteStatusFalse(cartItemId);
        if (!exists) {
            throw new DomainException(ExceptionType.CART_ITEM_NOT_FOUND);
        }

        // 2. 권한 확인과 함께 조회
        return cartItemRepository.findByIdAndMemberIdAndDeleteStatusFalse(cartItemId, memberId)
                .orElseThrow(() -> new DomainException(ExceptionType.UNAUTHORIZED_ERROR));
    }
}
