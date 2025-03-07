package com.tutti.server.core.example.application;

import com.tutti.server.core.example.payload.ExampleRegisterRequest;
import com.tutti.server.core.example.payload.ExampleSearchResponse;

public interface ExampleService {

    void create(ExampleRegisterRequest request);

    void modify();

    void remove();

    ExampleSearchResponse find();

}
