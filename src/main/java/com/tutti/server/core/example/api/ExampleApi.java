package com.tutti.server.core.example.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/examples")
public class ExampleApi implements ExampleApiSpec {

        @GetMapping
        public void get() {

    }

    @PostMapping
    public void register() {

    }

    @PutMapping("{exampleId}")
    public void modify(@PathVariable("exampleId") Long exampleId) {

    }

    @DeleteMapping("{exampleId}")
    public void remove(@PathVariable("exampleId") Long exampleId) {

    }

}
