package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;

public interface FaqTopViewedListService {

    List<FaqResponse> getTopFaqs(int limit);
}
