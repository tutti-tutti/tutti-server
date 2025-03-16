package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;

public interface FaqTopViewedListService {

    List<FaqResponse> getTopFaqs(int limit);
}
