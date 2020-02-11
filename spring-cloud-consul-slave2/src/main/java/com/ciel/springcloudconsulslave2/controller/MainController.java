package com.ciel.springcloudconsulslave2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    @GetMapping("/")
    public Object index(){

        return Map.of("MSG","WELCOME - SLAVE2");
    }

    @GetMapping("/list")
    public Object list(){
        return List.of(Map.of("name","xiapeixin"),
                Map.of("name","cielswift"),Map.of("name","FORM_slave2"));
    }

    @GetMapping("/flower")
    public Object flower(){
        return List.of(Map.of("name","xiapeixin"),
                Map.of("name","cielswift"),Map.of("name","FORM_slave2"));
    }
}
