package com.tutti.server.core.faq.config;

import com.tutti.server.core.faq.domain.FaqMainCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FaqMainCategoryConverter implements Converter<String, FaqMainCategory> {

    @Override
    public FaqMainCategory convert(String source) {
        return FaqMainCategory.fromDisplayName(source);
    }
}
