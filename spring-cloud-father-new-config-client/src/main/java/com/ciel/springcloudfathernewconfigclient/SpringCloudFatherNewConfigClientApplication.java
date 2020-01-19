package com.ciel.springcloudfathernewconfigclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableEurekaClient //服务注册eureka ,表示可以被Eureka注册中心发现
@EnableAspectJAutoProxy //代理

public class SpringCloudFatherNewConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewConfigClientApplication.class, args);
    }

    //cloud分布式配置中心Server端Client端; 获取不到配置问题排查; 修改git文件更新项目中的配置; 配置中敏感数据加密;cloud的消息总线
}
    