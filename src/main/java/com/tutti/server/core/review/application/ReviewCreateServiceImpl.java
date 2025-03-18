package com.tutti.server.core.review.application;

import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.review.api.SentimentApi;
import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCreateServiceImpl implements ReviewCreateService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final SentimentApi sentimentApi;

    @Transactional
    public ReviewCreateResponse createReview(ReviewCreateRequest req) {
        var member = memberRepository.findOne(req.memberId());
        var sentiment = sentimentApi.analyzeSentiment(new SentimentRequest(req.content()));

        var review = Review.builder()
            .member(member)
            .productId(req.productId())
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
