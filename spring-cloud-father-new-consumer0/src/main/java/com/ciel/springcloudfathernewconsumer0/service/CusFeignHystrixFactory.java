package com.ciel.springcloudfathernewconsumer0.service;

import com.ciel.api.CustomFeignHystrixFactory;
import com.ciel.springcloudfathernewconsumer0.fallback.FeignHystrixFallbackFactory;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 使用 fallbackFactory
 */
@FeignClient(contextId = "sp-producer-hystrix-2",name="SPRINGCLOUD-PRODUCER",
        fallbackFactory = FeignHystrixFallbackFactory.class ,path = "/producer")
public interface CusFeignHystrixFactory extends CustomFeignHystrixFactory {

    /**
     * feign 请求携带header
     */
    //@Headers( {"cache-control: no-cache", "username: wangyong@xxx.com"})
}
