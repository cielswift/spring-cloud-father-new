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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程隔离
 */
@Service
public class HystrixIsolation {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoadBalancerClient balancerClient;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 线程隔离的实现
     *  groupKey : 为每个producer(服务)设置标识, 一个group使用一个线程池; 默认getClass().getSimpleName();
     *  commandKey : consumer的接口名称; 默认当前方法名getUser
     *  threadPoolKey: 线程池名称,相同的线程池名称是一个,默认是groupKey;
     *
     */
    @HystrixCommand(
            //线程组名
            groupKey = "consumer_thread",
            //consumer接口名称
            commandKey = "getUser",
            //线程池名
            threadPoolKey = "consumer_thread_pool",
            threadPoolProperties = {
                    //线程池大小,默认10;
                    @HystrixProperty(name = HystrixPropertiesManager.CORE_SIZE, value = "30"),
                    //最大队列长度,默认-1,无限制;
                    @HystrixProperty(name = HystrixPropertiesManager.MAX_QUEUE_SIZE, value = "100"),
                    //线程存活时间,单位是分钟,默认1;
                    @HystrixProperty(name = HystrixPropertiesManager.KEEP_ALIVE_TIME_MINUTES, value = "2"),
                    //拒绝请求的临界值,当超过后拒绝请求; 调用fallback;默认5
                    @HystrixProperty(name = HystrixPropertiesManager.QUEUE_SIZE_REJECTION_THRESHOLD, value = "15"),
            },
            fallbackMethod = "fallback")
    public List<User> getUser() {

        System.out.println(Thread.currentThread().getName());

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
        temp.setName("MGSSSSSSSS返回托底数据++++++++++");
        return List.of(temp);
    }

    //线程隔离的对比方法
    public void showThread() {
        System.out.println("ProductService.showThread():" + Thread.currentThread().getName());
    }


}
