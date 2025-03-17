package com.tutti.server.core.review.application;

public interface ReviewDeleteService {

    boolean deleteReview(Long reviewId, String userEmail);
}
