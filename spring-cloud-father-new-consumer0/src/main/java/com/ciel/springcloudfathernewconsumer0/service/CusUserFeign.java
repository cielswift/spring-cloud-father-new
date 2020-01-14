package com.ciel.springcloudfathernewconsumer0.service;

import com.ciel.api.CustomFeign;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 使用@FeignClient注解  name属性指定服务提供者的应用名,并且加上项目名称
 */
@FeignClient(name="SPRINGCLOUD-PRODUCER/producer")
public interface CusUserFeign extends CustomFeign {

}
