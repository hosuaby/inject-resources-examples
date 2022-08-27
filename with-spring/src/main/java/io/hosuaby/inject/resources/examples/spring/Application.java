package io.hosuaby.inject.resources.examples.spring;

import com.adelean.inject.resources.spring.EnableResourceInjection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableResourceInjection    // <- enable resource-inject
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
