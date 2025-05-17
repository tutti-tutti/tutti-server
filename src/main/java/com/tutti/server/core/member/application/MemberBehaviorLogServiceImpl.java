package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.BehaviorType;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.MemberBehaviorLog;
import com.tutti.server.core.member.domain.MemberCategoryScore;
import com.tutti.server.core.member.domain.MemberTagScore;
import com.tutti.server.core.member.infrastructure.MemberBehaviorLogRepository;
import com.tutti.server.core.member.infrastructure.MemberCategoryScoreRepository;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.infrastructure.MemberTagScoreRepository;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductCategory;
import com.tutti.server.core.product.infrastructure.ProductCategoryMapRepository;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import com.tutti.server.core.tag.domain.ProductTag;
import com.tutti.server.core.tag.domain.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberBehaviorLogServiceImpl implements MemberBehaviorLogService {

    private final MemberBehaviorLogRepository behaviorLogRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final MemberCategoryScoreRepository categoryScoreRepository;
    private final ProductCategoryMapRepository productCategoryMapRepository;
    private final MemberTagScoreRepository memberTagScoreRepository;

    @Override
    public void log(Member member, Product inputProduct, BehaviorType behaviorType) {

        Product product = productRepository.findWithTagsById(inputProduct.getId())
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_NOT_FOUND));

        // 1. 행동 로그 저장
        MemberBehaviorLog log = MemberBehaviorLog.builder()
                .member(member)
                .product(product)
                .behaviorType(behaviorType)
                .build();
        behaviorLogRepository.save(log);

        // 2. 점수 계산
        int point = switch (behaviorType) {
            case VIEW -> 1;
            case WISHLIST -> 3;
            case WISHLIST_CANCEL -> -3;
            case CART_ADD -> 5;
            case PURCHASE -> 10;
        };

        // 3. 상품의 카테고리 가져오기
        ProductCategory category = productCategoryMapRepository
                .findFirstByProductIdAndDeleteStatusFalse(product.getId())
                .orElseThrow(() -> new DomainException(ExceptionType.CATEGORY_NOT_FOUND))
                .getCategory();

        while (category.getParentCategory() != null) {
            category = category.getParentCategory();
        }

        // 4. 기존 점수 있으면 누적, 없으면 새로 생성
        MemberCategoryScore score = categoryScoreRepository
                .findByMemberIdAndCategoryId(member.getId(), category.getId())
                .orElse(null);

        if (score == null) {
            score = MemberCategoryScore.builder()
                    .member(member)
                    .category(category)
                    .score(point)
                    .build();
        } else {
            score.addScore(point);
        }

        // 5. 저장
        categoryScoreRepository.save(score);

        //태그 점수 반영
        List<Tag> tags = product.getProductTags().stream()
                .map(ProductTag::getTag)
                .toList();

        for (Tag tag : tags) {
            MemberTagScore tagScore = memberTagScoreRepository
                    .findByMemberIdAndTagId(member.getId(), tag.getId())
                    .orElse(null);

            if (tagScore == null) {
                tagScore = MemberTagScore.builder()
                        .member(member)
                        .tag(tag)
                        .score(point)
                        .build();
            } else {
                tagScore.addScore(point);
            }

            memberTagScoreRepository.save(tagScore);
        }
    }
}
