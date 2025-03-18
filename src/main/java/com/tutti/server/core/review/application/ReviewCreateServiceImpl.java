package com.tutti.server.core.review.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.review.api.SentimentApi;
import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.review.payload.response.SentimentResponse;
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
    public ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest) {
        Member member = memberRepository.findOne(reviewCreateRequest.memberId());

        String reviewImagesString = String.join(",", reviewCreateRequest.reviewImages());

        SentimentResponse sentimentResponse = sentimentApi.analyzeSentiment(
            new SentimentRequest(reviewCreateRequest.content())
        );

        Review review = Review.builder()
            .member(member)
            .productId(reviewCreateRequest.productId())
            .nickname(reviewCreateRequest.nickname())
            .rating(reviewCreateRequest.rating())
            .content(reviewCreateRequest.content())
            .reviewImageUrls(reviewImagesString)
            .sentiment(sentimentResponse.sentiment())
            .sentimentProbability(sentimentResponse.probability())
            .build();

        reviewRepository.save(review);

        return new ReviewCreateResponse(
            review.getId(),
            review.getMember().getId(),
            review.getProductId(),
            review.getNickname(),
            review.getRating(),
            review.getContent(),
            review.getReviewImageUrls(),
            review.getSentiment(),
            review.getSentimentProbability(),
            review.getCreatedAt().toString()
        );
    }
}
