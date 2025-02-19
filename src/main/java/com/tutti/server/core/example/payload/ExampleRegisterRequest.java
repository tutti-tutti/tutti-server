package com.tutti.server.core.example.payload;

import com.tutti.server.core.example.domain.Example;

public record ExampleRegisterRequest(String name, String description) {

    public Example toEntity() {
        return Example.builder().name(name).description(description).build();
    }
}
