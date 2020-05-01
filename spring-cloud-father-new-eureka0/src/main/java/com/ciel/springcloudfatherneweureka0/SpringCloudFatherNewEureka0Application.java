package com.ciel.springcloudfatherneweureka0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer  //启动注册中心
@SpringBootApplication
public class SpringCloudFatherNewEureka0Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewEureka0Application.class, args);
    }

}
