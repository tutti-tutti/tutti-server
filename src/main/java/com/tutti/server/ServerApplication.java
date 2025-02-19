package com.tutti.server;

import com.tutti.server.core.example.domain.Example;
import com.tutti.server.core.example.infrastructure.ExampleRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(ExampleRepository exampleRepository) {
        return (args) -> {
            exampleRepository.save(Example.builder().build());
        };
    }

}
