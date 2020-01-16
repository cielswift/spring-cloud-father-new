package com.ciel.springcloudfathernewconsumer0.service;

import com.ciel.api.CustomFeignHystrixFactory;
import com.ciel.springcloudfathernewconsumer0.fallback.FeignHystrixFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 使用 fallbackFactory
 */
@FeignClient(contextId = "sp-producer-hystrix-2",name="SPRINGCLOUD-PRODUCER/producer",
        fallbackFactory = FeignHystrixFallbackFactory.class )
public interface CusFeignHystrixFactory extends CustomFeignHystrixFactory {


}
