package com.tutti.server.core.review.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"5", "4", "3", "2", "1"})
public record RatingResponse(
        @JsonProperty("5") long fiveStar,
        @JsonProperty("4") long fourStar,
        @JsonProperty("3") long threeStar,
        @JsonProperty("2") long twoStar,
        @JsonProperty("1") long oneStar
) {

}
