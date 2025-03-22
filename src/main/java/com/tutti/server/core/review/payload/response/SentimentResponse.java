package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "감정 분석 응답")
public record SentimentResponse(
    @Schema(description = "리뷰 내용",
        example = "보라색키보드로 유명한 타사 기계식 키보드 거진 15만원 돈 주고 샀는데 새 키보드가 수평 안 맞고 흔들리는 걸 고객책임으로 전가하다가, "
            + "as 보내니 문제 없었다고 거짓말 치고선 수평 맞춰서 보내주더라고요..? "
            + "정 털려서 쓰지 않고 판 뒤에 기계식이랑은 안 맞나 하다가 독거미 유명하대서 구입했어요. "
            + "이마저도 안 맞으면 기계신은 안 쓰려고 했구요. "
            + "근데 색감 너무 예쁘고, 타건감도 좋아요. "
            + "전에 저소음 썼었어서 이번엔 도각도각 소리가 나는 걸로 골랐는데 소리도 너무 좋습니다. "
            + "불빛도 세기나 색깔 선택 가능하고 깔끔하고 더할 나위 없이 좋네요. 양품 감사합니다 잘 쓸게요.")
    String review_text,
    @Schema(description = "긍정 or 부정", example = "positive")
    String sentiment,
    @Schema(description = "긍정 확률", example = "57.11")
    double probability
) {

}
