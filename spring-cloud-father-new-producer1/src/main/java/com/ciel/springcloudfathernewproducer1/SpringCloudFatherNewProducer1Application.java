package com.ciel.springcloudfathernewproducer1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient  //服务注册eureka ,表示可以被Eureka注册中心发现
@ComponentScan(basePackages = "com.ciel")  //扫描整个ciel下的包
@EnableAspectJAutoProxy  //开启基于注解的aop
@MapperScan("com.ciel.common.mapper")
/**
 * @Transactional 注解应该只被应用到 public 方法上

 * 默认情况下，只有来自外部的方法调用才会被AOP代理捕获，
 * 也就是，类内部方法调用本类内部的其他方法并不会引起事务行为，即使被调用方法使用@Transactional注解进行修饰。
 *
 * 开启事务,order指定aop的执行顺序,在其他aop(cache)之前执行;
 */
@EnableTransactionManagement(order = Ordered.HIGHEST_PRECEDENCE)

@EnableHystrix //hystrix 服务监控
@EnableHystrixDashboard //hystrixDashboard 服务监控

@EnableAuthorizationServer //整合oauth2
public class SpringCloudFatherNewProducer1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewProducer1Application.class, args);
    }

}
