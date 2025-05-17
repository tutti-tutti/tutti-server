package com.tutti.server.core.product.application;

import com.tutti.server.core.member.application.MemberBehaviorLogService;
import com.tutti.server.core.member.domain.BehaviorType;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberCategoryScoreRepository;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.infrastructure.MemberTagScoreRepository;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.product.domain.ProductLike;
import com.tutti.server.core.product.infrastructure.ProductCategoryMapRepository;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.product.infrastructure.ProductLikeRepository;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductOptionResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.product.payload.response.ProductSliceResponse;
import com.tutti.server.core.sku.domain.Sku;
import com.tutti.server.core.sku.infrastructure.SkuRepository;
import com.tutti.server.core.store.domain.Store;
import com.tutti.server.core.store.infrastructure.StoreRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final SkuRepository skuRepository;
    private final StoreRepository storeRepository;
    private final ProductLikeRepository productLikeRepository;
    private final MemberRepository memberRepository;
    private final MemberBehaviorLogService memberBehaviorLogService;
    private final MemberTagScoreRepository memberTagScoreRepository;
    private final MemberCategoryScoreRepository memberCategoryScoreRepository;
    private final ProductCategoryMapRepository productCategoryMapRepository;

    @Override
    public List<ProductResponse> getAllProductsByCreated() {
        // 생성일자 기준 내림차순으로 모든 상품 조회
        List<Product> products = productRepository.findAllByOrderByCreatedAtDesc();

        // ProductResponse 리스트로 변환
        return products.stream()
                .map(product -> {
                    // 해당 상품의 ProductItem 중 가장 낮은 판매가격을 가진 항목 찾기
                    ProductItem lowestPriceItem = productItemRepository
                            .findFirstByProductIdOrderBySellingPriceAsc(product.getId())
                            .orElseThrow(() -> new DomainException(
                                    ExceptionType.PRODUCT_ITEM_NOT_FOUND));

                    return ProductResponse.fromEntity(
                            product,
                            lowestPriceItem,
                            product.getStoreId()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductSliceResponse getAllProductsByCreated(Long cursorId, int size) {
        // 페이지 크기 + 1만큼 상품 조회하여 다음 페이지 존재 여부 확인
        List<Product> products = productRepository.findProductsByCursorId(cursorId, size + 1);

        // 다음 페이지 존재 여부 확인
        boolean hasNext = products.size() > size;

        // 실제 반환할 데이터 개수 설정
        int contentSize = hasNext ? size : products.size();

        // 다음 페이지가 있으면 마지막 상품을 제외한 목록만 반환
        List<Product> result = hasNext ? products.subList(0, size) : products;

        // 다음 커서 설정 (다음 페이지가 없으면 null)
        Long nextCursor = hasNext ? result.get(result.size() - 1).getId() : null;

        // ProductResponse 리스트로 변환
        List<ProductResponse> content = result.stream()
                .map(product -> {
                    // 해당 상품의 ProductItem 중 가장 낮은 판매가격을 가진 항목 찾기
                    ProductItem lowestPriceItem = productItemRepository
                            .findFirstByProductIdOrderBySellingPriceAsc(product.getId())
                            .orElseThrow(() -> new DomainException(
                                    ExceptionType.PRODUCT_ITEM_NOT_FOUND));

                    return ProductResponse.fromEntity(
                            product,
                            lowestPriceItem,
                            product.getStoreId()
                    );
                })
                .collect(Collectors.toList());

        // ProductSliceResponse 생성 및 반환
        return ProductSliceResponse.fromEntity(hasNext, nextCursor, contentSize, content);
    }

    @Override
    public ProductSliceResponse getAllProductsBySearchWord(Long cursorId, int size,
            String searchWord) {
        // 페이지 크기 + 1만큼 상품 조회하여 다음 페이지 존재 여부 확인
        List<Product> products = productRepository.findProductsBySearchWord(cursorId, size + 1,
                searchWord);

        // 다음 페이지 존재 여부 확인
        boolean hasNext = products.size() > size;

        // 실제 반환할 데이터 개수 설정
        int contentSize = hasNext ? size : products.size();

        // 다음 페이지가 있으면 마지막 상품을 제외한 목록만 반환
        List<Product> result = hasNext ? products.subList(0, size) : products;

        // 다음 커서 설정 (다음 페이지가 없으면 null)
        Long nextCursor = hasNext ? result.get(result.size() - 1).getId() : null;

        // ProductResponse 리스트로 변환
        List<ProductResponse> content = result.stream()
                .map(product -> {
                    // 해당 상품의 ProductItem 중 가장 낮은 판매가격을 가진 항목 찾기
                    ProductItem lowestPriceItem = productItemRepository
                            .findFirstByProductIdOrderBySellingPriceAsc(product.getId())
                            .orElseThrow(() -> new DomainException(
                                    ExceptionType.PRODUCT_ITEM_NOT_FOUND));

                    return ProductResponse.fromEntity(
                            product,
                            lowestPriceItem,
                            product.getStoreId()
                    );
                })
                .collect(Collectors.toList());

        // ProductSliceResponse 생성 및 반환
        return ProductSliceResponse.fromEntity(hasNext, nextCursor, contentSize, content);
    }

    @Override
    public ProductItemResponse getProductItemsWithOptions(Long productId) {
        // 1. 주어진 productId에 대한 모든 ProductItem 목록 가져오기
        List<ProductItem> productItems = getProductItemWithOptions(productId);
        List<ProductOptionResponse> options = productItems.stream()
                .map(ProductOptionResponse::from)
                .toList();

        // 2. 상품 조회
        Product product = productRepository.findOne(productId);

        // 3. 스토어 조회
        Store store = storeRepository.findOne(product.getStoreId().getId());

        // 4. 모든 ProductItem에 대한 SKU 정보 조회
        List<Sku> skuList = getSkuListByProductItems(productItems);

        // SKU 존재 여부 확인 및 최소 재고 SKU 찾기
        Sku minStockSku = null;
        if (skuList.size() == productItems.size()) {
            minStockSku = findSkuWithMinimumStock(skuList);
        }
        // 없으면 예외처리

        // 5. fromEntities 메서드를 사용하여 통합된 응답 생성
        return ProductItemResponse.fromEntity(product, options, store, minStockSku);
    }


    // productId를 받아서 옵션을 매핑하고 productItems 반환하는 메서드
    @Override
    public List<ProductItem> getProductItemWithOptions(Long productId) {
        // 상품 조회
        Product product = productRepository.findOne(productId);

        // 해당 상품의 삭제되지 않은 ProductItem들 조회
        List<ProductItem> productItems = productItemRepository.findActiveItemsByProductId(
                productId);

        if (productItems.isEmpty()) {
            throw new DomainException(ExceptionType.PRODUCT_ITEM_NOT_FOUND);
        }

        return productItems;
    }


    @Override
    public List<ProductResponse> getAllProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findProductsByCategoryId(categoryId);

        return products.stream()
                .map(product -> {
                    ProductItem lowestPriceItem = productItemRepository
                            .findFirstByProductIdOrderBySellingPriceAsc(product.getId())
                            .orElseThrow(() -> new DomainException(
                                    ExceptionType.PRODUCT_ITEM_NOT_FOUND));

                    return ProductResponse.fromEntity(
                            product,
                            lowestPriceItem,
                            product.getStoreId()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Sku> getSkuListByProductItems(List<ProductItem> productItems) {
        // 모든 ProductItem ID를 추출
        List<Long> productItemIds = productItems.stream()
                .map(ProductItem::getId)
                .collect(Collectors.toList());

        // ProductItemIds에 대한 Sku 목록 조회
        return skuRepository.findByProductItemIds(productItemIds);
    }

    @Override
    public List<ProductResponse> getProductsByLikes(int size) {
        // 좋아요 수 기준 내림차순으로 상위 size개 상품 조회
        List<Product> products = productRepository.findTopByOrderByLikeCountDesc(size);

        // ProductResponse 리스트로 변환
        return products.stream()
                .map(product -> {
                    // 해당 상품의 ProductItem 중 가장 낮은 판매가격을 가진 항목 찾기
                    ProductItem lowestPriceItem = productItemRepository
                            .findFirstByProductIdOrderBySellingPriceAsc(product.getId())
                            .orElseThrow(() -> new DomainException(
                                    ExceptionType.PRODUCT_ITEM_NOT_FOUND));

                    return ProductResponse.fromEntity(
                            product,
                            lowestPriceItem,
                            product.getStoreId()
                    );
                })
                .collect(Collectors.toList());
    }

    public Sku findSkuWithMinimumStock(List<Sku> skus) {
        if (skus.isEmpty()) {
            throw new DomainException(ExceptionType.SKU_NOT_FOUND);
        }

        return skus.stream()
                .min((sku1, sku2) ->
                        Integer.compare(sku1.getStockQuantity(), sku2.getStockQuantity()))
                .get();
    }

    //상품 좋아요
    @Override
    public void likeProduct(Long productId, Long memberId) {
        if (!productLikeRepository.existsByProductIdAndMemberId(productId, memberId)) {
            Product product = productRepository.findOne(productId);
            Member member = memberRepository.findOne(memberId);
            productLikeRepository.save(ProductLike.builder()
                    .product(product)
                    .member(member)
                    .build());
            product.increaseLikeCount();
            memberBehaviorLogService.log(member, product, BehaviorType.WISHLIST);
        }
    }

    @Override
    public void unlikeProduct(Long productId, Long memberId) {
        productLikeRepository.deleteByProductIdAndMemberId(productId, memberId);
        Product product = productRepository.findOne(productId); // likeCount 감소용
        product.decreaseLikeCount();

        Member member = memberRepository.findOne(memberId);
        memberBehaviorLogService.log(member, product, BehaviorType.WISHLIST_CANCEL);
    }

    @Override
    public boolean isProductLiked(Long productId, Long memberId) {
        return productLikeRepository.existsByProductIdAndMemberId(productId, memberId);
    }

    @Override
    public int calculateMatchScore(Long memberId, Long productId) {
        Product product = getProductWithTagsById(productId);

        // 1. 회원의 태그 점수 Map 조회
        Map<Long, Integer> memberTagScoreMap = getMemberTagScoreMap(memberId);

        // 2. 회원의 카테고리 점수 Map 조회
        Map<Long, Integer> memberCategoryScoreMap = getMemberCategoryScoreMap(memberId);

        // 3. 상품의 태그 ID 목록 추출
        Set<Long> productTagIds = extractProductTagIds(product);
        Map<Long, Long> productTagFrequencies = extractProductTagFrequencies(product);

        // 4. 상품의 최상위 카테고리 ID 조회
        Long productCategoryId = findTopLevelCategoryId(productId);

        // 5 & 6. 태그 점수와 카테고리 점수 계산 후 합산
        int tagScore = calculateTagScore(productTagIds, memberTagScoreMap, productTagFrequencies);
        int categoryScore = calculateCategoryScore(productCategoryId, memberCategoryScoreMap);

        return tagScore + categoryScore;
    }

    // 상품 ID로 태그 정보를 포함한 상품 엔티티 조회
    public Product getProductWithTagsById(Long productId) {
        return productRepository.findWithTagsById(productId)
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_NOT_FOUND));
    }


    // 회원의 태그별 점수 Map 조회
    public Map<Long, Integer> getMemberTagScoreMap(Long memberId) {
        return memberTagScoreRepository.findAllByMemberId(memberId)
                .stream()
                .collect(Collectors.toMap(
                        mts -> mts.getTag().getId(),
                        mts -> mts.getScore()
                ));
    }


    // 회원의 카테고리별 점수 Map 조회
    public Map<Long, Integer> getMemberCategoryScoreMap(Long memberId) {
        return memberCategoryScoreRepository.findAllByMemberId(memberId)
                .stream()
                .collect(Collectors.toMap(
                        mcs -> mcs.getCategory().getId(),
                        mcs -> mcs.getScore()
                ));
    }

    // 상품에서 태그 ID 집합 추출
    public Set<Long> extractProductTagIds(Product product) {
        return product.getProductTags().stream()
                .map(productTag -> productTag.getTag().getId())
                .collect(Collectors.toSet());
    }

    public Map<Long, Long> extractProductTagFrequencies(Product product) {
        return product.getProductTags().stream()
                .collect(Collectors.groupingBy(
                        productTag -> productTag.getTag().getId(),
                        Collectors.counting()
                ));
    }

    // 태그 기반 매칭 점수 계산
    public int calculateTagScore(Set<Long> productTagIds, Map<Long, Integer> memberTagScoreMap,
            Map<Long, Long> productTagFrequencies) {

        int score = 0;
        for (Long tagId : productTagIds) {
            if (memberTagScoreMap.containsKey(tagId)) {
                int memberTagScore = memberTagScoreMap.get(tagId);
                long frequency = productTagFrequencies.getOrDefault(tagId, 1L);

                // 빈도수 * 회원의 태그 선호 점수
                score += memberTagScore * frequency;
            }
        }
        return score;
    }

    // 카테고리 기반 매칭 점수 계산
    public int calculateCategoryScore(Long productCategoryId,
            Map<Long, Integer> memberCategoryScoreMap) {
        return (productCategoryId != null && memberCategoryScoreMap.containsKey(productCategoryId))
                ? memberCategoryScoreMap.get(productCategoryId)
                : 0;
    }

    // 상품의 최상위 카테고리 ID 조회
    public Long findTopLevelCategoryId(Long productId) {
        return productCategoryMapRepository
                .findFirstByProductIdAndDeleteStatusFalse(productId)
                .map(map -> {
                    var category = map.getCategory();
                    while (category.getParentCategory() != null) {
                        category = category.getParentCategory();
                    }
                    return category.getId();
                })
                .orElse(null);
    }

    @Override
    public List<ProductResponse> recommendProductsForMember(Long memberId,
            int size) {

        // 1. 회원의 선호 카테고리 점수 Map 조회
        Map<Long, Integer> categoryScoreMap = getMemberCategoryScoreMap(memberId);

        // 2. 가장 점수 높은 카테고리 하나 선택
        Long bestCategoryId = categoryScoreMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (bestCategoryId == null) {
            return List.of(); // 또는 예외 처리
        }
        // 선호 카테고리 기준 최근 100개 상품 조회
        Pageable top100 = PageRequest.of(0, 100);
        List<Product> products = productRepository.findTop100ByCategoryIdOrderByCreatedAtDesc(
                bestCategoryId, top100);

        // 각 상품에 대한 매칭 점수 계산 및 정렬
        return products.stream()
                .map(product -> Map.entry(product, calculateMatchScore(memberId, product.getId())))
                .sorted((a, b) -> b.getValue() - a.getValue()) // 점수 높은 순
                .limit(size)
                .map(Map.Entry::getKey)
                .map(this::convertToProductResponse)
                .toList();
    }

    public ProductResponse convertToProductResponse(Product product) {
        ProductItem lowestPriceItem = productItemRepository
                .findFirstByProductIdOrderBySellingPriceAsc(product.getId())
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_ITEM_NOT_FOUND));

        return ProductResponse.fromEntity(product, lowestPriceItem, product.getStoreId());
    }
}
