package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String home() {
        return "index.html"; // static folder - index.html
    }

    @GetMapping("/send")
    public ModelAndView send(@RequestParam(value = "name") String name) {
        String url = "http://myapp1-service/api/greet?name=" + name;
        String response = restTemplate.getForObject(url, String.class);
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("message", response);
        return modelAndView;
    }
}
