package com.ciel.springcloudfathernewproducer1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient  //服务注册eureka ,表示可以被Eureka注册中心发现
@ComponentScan(basePackages = "com.ciel")  //扫描整个ciel下的包
@EnableAspectJAutoProxy  //开启基于注解的aop
@MapperScan("com.ciel.common.mapper")
@EnableTransactionManagement //开启事务
public class SpringCloudFatherNewProducer1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewProducer1Application.class, args);
    }

}
