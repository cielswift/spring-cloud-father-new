package com.ciel.springcloudfathernewproducer0.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MainController {

    @GetMapping("/")
    public Object index(){
        return Map.of("name","producer0");
    }
}
