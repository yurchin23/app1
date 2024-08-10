package com.example.myapp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            System.out.println("Application has started!");
        };
    }
}
