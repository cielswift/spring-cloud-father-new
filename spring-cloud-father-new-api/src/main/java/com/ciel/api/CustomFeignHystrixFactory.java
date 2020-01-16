package com.ciel.api;

import com.ciel.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/feignHystrixFactory")
public interface CustomFeignHystrixFactory {

    @GetMapping("/fhf")
    public List<User> fhf();
}
