package com.ciel.springcloudconsulslave1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="consul-producer",fallback = ImplFallback.class,path = "/producer")
public interface FeignForProducer {

    /**
     *映射,方法对应的请求地址
     */
    @GetMapping("/flower")
    public Object flower();

    @GetMapping("/water")
    public Object water();
}
