package com.ciel.springcloudfathernewconsumer0.service;

import com.ciel.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.bouncycastle.math.ec.NafL2RMultiplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 信号量隔离
 */
@Service
public class HystrixSignal {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoadBalancerClient balancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(
            fallbackMethod = "fallback",
            commandProperties = {
                    // 信号量隔离配置项;只有SEMAPHORE和THREAD;
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "SEMAPHORE"),
                    // 超时时间,默认1000ms;THREAD模式下超过自动中断,SEMAPHORE等待执行完再判断是否超时;
                    @HystrixProperty(name=HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_TIME_IN_MILLISECONDS,value = "1000"),
                    //是否打开超时线程中断 THREAD 模式下有效;
                   // @HystrixProperty(name=HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_TIMEOUT,value = "true"),
                    //信号量最大并度,默认10; SEMAPHORE 模式下有效
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS, value = "100"),
                    //fallback 最大并发度默认10; SEMAPHORE 模式下有效
                    @HystrixProperty(name=HystrixPropertiesManager.FALLBACK_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS,value="100")
            })

    public List<User> allUser() {
        //选择调用的服务的名称
        List<User> forObject = restTemplate.getForObject
                ("http://SPRINGCLOUD-PRODUCER/producer/role/rs", List.class);

        return forObject;
    }


    /**
     * 返回托底数据(这里的参数要与调用托底方法的方法一致)
     *
     * @return
     */
    public List<User> fallback() {

        User temp = new User();
        temp.setName("MGSSSSSSSS返回托<><><><><><><底数据++++++++++");
        return List.of(temp);
    }


}
