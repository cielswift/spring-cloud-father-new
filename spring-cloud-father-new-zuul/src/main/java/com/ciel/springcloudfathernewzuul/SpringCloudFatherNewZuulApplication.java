package com.ciel.springcloudfathernewzuul;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@SpringBootApplication(scanBasePackages = "com.ciel.springcloudfathernewzuul" )
@EnableEurekaClient //服务注册eureka ,表示可以被Eureka注册中心发现
@EnableAspectJAutoProxy //代理

@EnableZuulProxy //开启路由,网关代理
@EnableDiscoveryClient  //无感知远程调用需要
@EnableCircuitBreaker //服务熔断 断路
/**
 * //包括：@SpringBootApplication、@EnableDiscoveryClient、@EnableCircuitBreaker，
 * 分别是SpringBoot注解、注册服务中心Eureka注解、断路器注解。对于SpringCloud来说，这是每一微服务必须应有的三个注解
 */
//@SpringCloudApplication

@EnableHystrix //hystrix 服务监控
@EnableHystrixDashboard //hystrixDashboard 服务监控
public class SpringCloudFatherNewZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewZuulApplication.class, args);
    }


    @Bean
    @Primary
    public RedisTemplate redisTemplateJSON(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(RedisSerializer.json());
        return redisTemplate;
    }

    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();

        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean =
                new ServletRegistrationBean<HystrixMetricsStreamServlet>(streamServlet);

        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
