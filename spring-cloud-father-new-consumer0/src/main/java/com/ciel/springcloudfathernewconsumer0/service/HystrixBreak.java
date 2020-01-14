package com.ciel.springcloudfathernewconsumer0.service;

import com.ciel.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务熔断
 */
@Service
public class HystrixBreak {

    @Autowired
    private LoadBalancerClient balancerClient;

    @Autowired
    private RestTemplate restTemplate;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 利用Hystrix进行服务熔断
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            // 默认 20 个;10s 内请求数大于 20 个时就启动熔断器，当请求符合熔断条件时将触发 getFallback()。
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "10"),
            // 请求错误率大于 50%时就熔断，然后 for 循环发起请求， 当请求符合熔断条件时将触发 getFallback()。
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50"),
            // 默认 5 秒;熔断多少秒后去尝试请求
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "5000")})
    public List<User> getUser(Integer id) {

        logger.warn("请求的id:" + id);

        if (id == 1) {
            throw new RuntimeException();
        }

        List<User> forObject = restTemplate.getForObject
                ("http://SPRINGCLOUD-PRODUCER/producer/role/rs", List.class);

        return forObject;
    }

    /**
     * 返回托底数据(这里的参数和返回值 要与调用托底方法的方法一致)
     *
     * @return
     */
    public List<User> fallback(Integer id) {
        logger.info("收到参数:" + String.valueOf(id));

        User temp = new User();
        temp.setName("MGSSSSSSSS返回托底数据");
        return List.of(temp);

    }

}
