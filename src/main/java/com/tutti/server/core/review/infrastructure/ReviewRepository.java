package com.tutti.server.core.review.infrastructure;

import com.tutti.server.core.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
