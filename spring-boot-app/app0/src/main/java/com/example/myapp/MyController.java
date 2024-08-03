package com.example.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MyController {

    @GetMapping("/")
    public String home() {
        return "index.html"; // static folder - index.html
    }

    @GetMapping("/send")
    public RedirectView send(@RequestParam(value = "name") String name) {
        String url = "http://localhost.app1.com/api/greet?name=" + name;
        return new RedirectView(url);
    }
}