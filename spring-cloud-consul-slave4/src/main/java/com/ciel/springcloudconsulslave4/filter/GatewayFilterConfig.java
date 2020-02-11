package com.ciel.springcloudconsulslave4.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayFilterConfig   {

    /**
     * 顺序
     * start
     * second pre filter
     * aaaaaaaaaaaaaaaaa
     * bbbbbbbbbbbbbbbbb
     * start
     * second pre filter
     * second post filter
     * chain
     * BBBBBBBBBBBBBBBBBBBBBBB
     * AAAAAAAAAAAAAAAAAA
     * second post filter
     * chain
     */

    /**
     *局部过滤器
     */

    @Bean(name = "aa")
    @Order(-1)
    public GatewayFilter aa(){
        return (exchange, chain) -> {

            System.out.println("aaaaaaaaaaaaaaaaa");
            exchange.getRequest();

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                System.out.println("AAAAAAAAAAAAAAAAAA");
            }));
        };

    }

    @Bean(name = "bb")
    @Order(0)
    public GatewayFilter bb(){
        return (exchange, chain) -> {

            System.out.println("bbbbbbbbbbbbbbbbb");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                System.out.println("BBBBBBBBBBBBBBBBBBBBBBB");
            }));
        };

    }
}
