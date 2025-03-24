package com.tutti.server.core.review.application;

import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.order.infrastructure.OrderItemRepository;
import com.tutti.server.core.product.infrastructure.ProductItemRepository;
import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCreateServiceImpl implements ReviewCreateService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final SentimentService sentimentService;
    private final OrderItemRepository orderItemRepository;
    private final ProductItemRepository productItemRepository;

    @Override
    @Transactional
    public ReviewCreateResponse createReview(ReviewCreateRequest req, Long memberId) {

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + memberId);
        boolean exists = orderItemRepository.existsByOrderMemberIdAndProductItemId(memberId,
                req.productItemId());
        if (!exists) {
            throw new DomainException(ExceptionType.UNAUTHORIZED_ERROR);
        }

        var member = memberRepository.findOne(memberId);
        var sentiment = sentimentService.analyzeSentiment(new SentimentRequest(req.content()));
        var productItem = productItemRepository.findOne(req.productItemId());

        var review = Review.builder()
                .member(member)
                .productItem(productItem)
                .nickname(req.nickname())
                .rating(req.rating())
                .content(req.content())
                .reviewImageUrls(String.join(",", req.reviewImages()))
                .sentiment(sentiment.sentiment())
                .sentimentProbability(sentiment.probability())
                .build();
        reviewRepository.save(review);

        return ReviewCreateResponse.from(review);
    }
}
