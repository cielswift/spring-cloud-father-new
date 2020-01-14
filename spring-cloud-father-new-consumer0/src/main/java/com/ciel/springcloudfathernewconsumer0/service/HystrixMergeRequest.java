package com.ciel.springcloudfathernewconsumer0.service;

import com.ciel.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Hystrix 请求合并
 */
@Service
public class HystrixMergeRequest {

    //两个注解 @HystrixCollapser(合并参数的设置) ,@HystrixCommand (真正调用provider的方法 形参必须是list不能是数组)

    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 利用Hystrix进行请求合并
     * 返回类型必须是Future,否则无法合并;
     * batchMethod 合并请求的方法;
     * scope 请求方式; REQUEST只对一个request的请求内的多次服务请求进行合并;GLOBAL单个应用内的所有线程的服务请求行合并
     */
    @HystrixCollapser(batchMethod = "batchUser", scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            collapserProperties = {
            // 请求时间间隔在 20ms 之内的请求会被合并为一个请求,默认 为 10ms
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "20"),
            // 设置触发批处理执行之前，在批处理中允许的最大请求数。
            @HystrixProperty(name = "maxRequestsInBatch", value = "200") })

    public Future<User> getUser(Integer id) {//consumer的controller调用方法,该方法返回Future类型

        logger.warn("==========调用了getUser方法==============");
        return null;
    }


    // 真正调用provider的方法 形参必须是list不能是数组
    @HystrixCommand
    public List<User> batchUser(List<Integer> ids) {


        for (Integer id : ids) {

            logger.warn("ID="+id);
        }

        List<User> list = new ArrayList<User>();
        User user = new User();
        user.setName("玉皇大帝");
        User user1 = new User();
        user1.setName("菩提老祖");
        User user2 = new User();
        user2.setName("原始天尊");

        list.add(user);
        list.add(user1);
        list.add(user2);

        logger.warn("-----------------获取到数据-----------------");
        return list;
    }

}
