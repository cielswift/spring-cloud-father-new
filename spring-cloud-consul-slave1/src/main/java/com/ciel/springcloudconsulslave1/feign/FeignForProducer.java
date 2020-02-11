package com.ciel.springcloudconsulslave1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="consul-producer",fallback = ImplFallback.class)
public interface FeignForProducer {

    /**
     *映射,方法对应的请求地址
     */
    @GetMapping("producer/flower")
    public Object flower();

    @GetMapping("producer/water")
    public Object water();
}
