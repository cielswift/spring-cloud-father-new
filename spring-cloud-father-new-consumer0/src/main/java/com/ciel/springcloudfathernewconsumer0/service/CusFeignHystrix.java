package com.ciel.springcloudfathernewconsumer0.service;

import com.ciel.api.CustomFeignHystrix;
import com.ciel.springcloudfathernewconsumer0.fallback.FeignHystrixFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Feign的底层封装了Ribbon的所有功能 ,故hystrix坐标不添加也能实现该功能 ,
 * 因此实启动类可以不加@EnableCircuitBreaker
 *
 * 需要再yml中 feign-hystris-enable=true;
 *
 * fallback 指定兜底(托底)类, 兜底类必须加入到springIOC中
 *
 * contextId 会作为bean的名称
 */
@FeignClient(contextId = "sp-producer-hystrix",name="SPRINGCLOUD-PRODUCER/producer",
        fallback= FeignHystrixFallback.class)
public interface CusFeignHystrix extends CustomFeignHystrix {

}
