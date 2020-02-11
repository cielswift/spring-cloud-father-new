package com.ciel.springcloudconsulslave4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient

//@EnableZuulProxy //开启路由, ;@EnableZuulProxy简单理解为@EnableZuulServer的增强版,gateway 不需要

public class SpringCloudConsulSlave4Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsulSlave4Application.class, args);
    }

    /**
     * 启动zipkin java -jar zipkin-server-2.12.2-exec.jar --logging.level.zipkin2=INFO
     *
     * 代码方式实现路由
     * @param builder
     * @return
     */
    @Autowired
    @Qualifier("aa")
    protected GatewayFilter aa;

    @Autowired
    @Qualifier("bb")
    protected GatewayFilter bb;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/sla/**")
                        .filters(f -> f.stripPrefix(1).filter(aa).filter(bb).addResponseHeader("X-Response-Default-Foo", "Default-Bar")
                        )
                        .uri("lb://consul-slave1")
                        .order(0)
                        .id("consul-slave1"))
//http://127.0.0.1:4013/zuul/sla/slave1/flower
                .route(r -> r.path("/producer/**")
                        .filters(f -> f.stripPrefix(1) //.filter(new TestGetWayFilter()).addResponseHeader("X-Response-Default-Foo", "Default-Bar")
                        )
                        .uri("lb://consul-producer")
                        .order(0)
                        .id("consul-producer"))
        //http://127.0.0.1:4013/zuul/producer/producer/flower
                .build();
    }
}
