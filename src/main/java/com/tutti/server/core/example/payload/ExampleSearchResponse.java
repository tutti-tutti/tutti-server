package com.tutti.server.core.example.payload;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import com.tutti.server.core.example.domain.Example;
import java.util.List;

public record ExampleSearchResponse(List<SimpleExample> examples) {

    public static ExampleSearchResponse of(List<Example> examples) {
        return examples.stream()
            .map(SimpleExample::from)
            .collect(collectingAndThen(toList(), ExampleSearchResponse::new));
    }

}