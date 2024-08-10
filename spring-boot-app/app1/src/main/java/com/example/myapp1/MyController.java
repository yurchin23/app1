package com.example.myapp1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/greet")
    public String greet(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/info")
    public String info() {
        return "This is a Spring Boot application with a REST endpoint!";
    }
}
