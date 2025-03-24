package com.tutti.server.core.review.application;

import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.order.application.OrderService;
import com.tutti.server.core.product.application.ProductService;
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
    private final ProductService productService;
    private final OrderService orderService;

    @Transactional
    public ReviewCreateResponse createReview(ReviewCreateRequest req) {

        boolean hasPurchasedProduct = orderService.hasPurchasedProduct(req.memberId(),
            req.productItemId());
        if (!hasPurchasedProduct) {
            throw new DomainException(ExceptionType.ORDER_ITEM_NOT_FOUND, "주문 내역이 존재하지 않습니다.");
        }

        var member = memberRepository.findOne(req.memberId());
        var sentiment = sentimentService.analyzeSentiment(new SentimentRequest(req.content()));
        var productItem = productService.getProductItemById(req.productItemId());

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
