package com.ciel.springcloudfathernewconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableEurekaClient //服务注册eureka ,表示可以被Eureka注册中心发现
@EnableAspectJAutoProxy //代理

@EnableConfigServer  //分布式配置
public class SpringCloudFatherNewConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewConfigApplication.class, args);
    }

}
