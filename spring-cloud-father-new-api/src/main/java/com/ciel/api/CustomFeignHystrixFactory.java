package com.ciel.api;

import com.ciel.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/feignHystrixFactory")
public interface CustomFeignHystrixFactory {

    /**
     * feign 请求携带一个头
     */
   // @GetMapping(value = "/fhf",headers = "Authentication=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.e")


    @GetMapping("/fhf")
    public List<User> fhf() throws InterruptedException;
}
