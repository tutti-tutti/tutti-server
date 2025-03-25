package com.tutti.server.core.review.utils;

import com.tutti.server.core.review.payload.response.RatingResponse;
import java.util.List;

public class RatingResponseFactory {

    public static RatingResponse fromStarCounts(List<Object[]> starCounts) {
        long one = 0L, two = 0L, three = 0L, four = 0L, five = 0L;

        for (int i = starCounts.size() - 1; i >= 0; i--) {
            Object[] row = starCounts.get(i);
            int star = ((Number) row[0]).intValue();
            long count = ((Number) row[1]).longValue();

            switch (star) {
                case 1 -> one = count;
                case 2 -> two = count;
                case 3 -> three = count;
                case 4 -> four = count;
                case 5 -> five = count;
            }
        }

        return RatingResponse.builder()
                .fiveStar(five)
                .fourStar(four)
                .threeStar(three)
                .twoStar(two)
                .oneStar(one)
                .build();
    }

    public static long calculateTotalCount(RatingResponse ratingResponse) {
        return ratingResponse.fiveStar()
                + ratingResponse.fourStar()
                + ratingResponse.threeStar()
                + ratingResponse.twoStar()
                + ratingResponse.oneStar();
    }
}
