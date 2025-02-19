package com.tutti.server.core.example.payload;

import com.tutti.server.core.example.domain.Example;

public record SimpleExample(Long id, String title, String description) {

    public static SimpleExample from(Example example) {
        return new SimpleExample(example.getId(), example.getName(), example.getDescription());
    }
}
