package com.ciel.springcloudfathernewconfigclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient //服务注册eureka ,表示可以被Eureka注册中心发现
@EnableAspectJAutoProxy //代理

@EnableScheduling   // 开启定时任务

public class SpringCloudFatherNewConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewConfigClientApplication.class, args);
    }

    //cloud消息总线刷新配置,刷新单个服务,集群的配置;cloud stream整合其他消息队列做流式处理;

}
    