package com.tutti.server.core.example.api;


import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "예시 문서")
public interface ExampleApiSpec {
    void get();

    void register();

    void modify(Long id);

    void remove(Long exampleId);
}
