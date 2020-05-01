package com.ciel.springcloudfathernewconsumer0.service;

import com.ciel.api.CustomFeign;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 使用@FeignClient注解  name属性指定服务提供者的应用名,path :项目根路径
 */
@FeignClient(name="SPRINGCLOUD-PRODUCER",path = "/producer")
public interface CusUserFeign extends CustomFeign {

}
