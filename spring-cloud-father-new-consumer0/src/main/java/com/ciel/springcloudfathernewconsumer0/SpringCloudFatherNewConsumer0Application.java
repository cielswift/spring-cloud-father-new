package com.ciel.springcloudfathernewconsumer0;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient //服务注册eureka ,表示可以被Eureka注册中心发现
@EnableAspectJAutoProxy  //@EnableAspectJAutoProxy(proxyTargetClass = true)  //开启基于注解的aop;使用cglib
@EnableCaching  //开启缓存功能

@EnableFeignClients //无感知远程调用 (包装了Ribbon)
/**
 * 和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到该服务。
 * 不同点：@EnableEurekaClient只适用于Eureka作为注册中心，
 * @EnableDiscoveryClient 可以是其他注册中心如Zookeeper、Consul等;
 */
@EnableDiscoveryClient  //(无感知远程调用需要)

@EnableCircuitBreaker   //添加熔断器,开启熔断

@EnableHystrix //hystrix 服务监控,容错保护
@EnableHystrixDashboard //hystrixDashboard 服务监控
public class SpringCloudFatherNewConsumer0Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewConsumer0Application.class, args);
    }

    //sharding-jdbc和mysql兼容问题;微服多路多级调用;zuul的路由网关;路由拦截异常处理;整合hystrix

    /**FEIGN 和 RIBBON 相关=============================================================================================*/

    /**
     * feign请求的调用耗时等,还需要在yml配置请求超时时间
     * NONE:不记录任何信息，默认值
     * BASIC:记录请求方法、请求 URL、状态码和用时
     * HEADERS:在 BASIC 基础上再记录一些常用信息
     * FULL:记录请求和相应的所有信息
     */
    @Bean
    public Logger.Level getLogs(){
        return Logger.Level.FULL;
    }

    @Bean
    @LoadBalanced()  //启用负载均衡机制  不能和@Autowired private LoadBalancerClient balancerClient; 同时使用 ;配合@EnableFeignClients
    public RestTemplate restTemplate() { //服务调用者
        //   return new RestTemplate();

        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getInterceptors()
//                .add(new BasicAuthenticationInterceptor("xia", "123", StandardCharsets.UTF_8));

        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName("utf-8"));
                break;
            }
        }

        return restTemplate;
    }

    /**
     * RoundRobinRule：轮询；
     * RandomRule：随机；
     * AvailabilityFilteringRule：会先过滤掉由于多次访问故障而处于断路器状态的服务，还有并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问；
     * WeightedResponseTimeRule：根据平均响应时间计算所有服务的权重，响应时间越快的服务权重越大被选中的概率越大。刚启动时如果统计信息不足，则使用RoundRobinRule（轮询）策略，等统计信息足够，会切换到WeightedResponseTimeRule；
     * RetryRule：先按照RoundRobinRule（轮询）策略获取服务，如果获取服务失败则在指定时间内进行重试，获取可用的服务；
     * BestAvailableRule：会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
     * ZoneAvoidanceRule：复合判断Server所在区域的性能和Server的可用性选择服务器；
     */
    @Bean
    @Primary
    public IRule changeRole() { //负载均衡策略
        return new AvailabilityFilteringRule();
    }

    /**
     * 自定义负载均衡
     *
     * @return
     */
    @Bean
    public IRule customIRule() {
        return new AbstractLoadBalancerRule() {

            /*
             total = 0 // 当total==5以后，我们指针才能往下走，
             index = 0 // 当前对外提供服务的服务器地址，
             total需要重新置为零，但是已经达到过一个5次，我们的index = 1
             */
            private int total = 0;             // 总共被调用的次数，目前要求每台被调用5次
            private int currentIndex = 0;    // 当前提供服务的机器号

            @Override
            public void initWithNiwsConfig(IClientConfig iClientConfig) {
            }

            @Override
            public Server choose(Object o) {

                ILoadBalancer lb = getLoadBalancer();

                if (lb == null) {
                    return null;
                }
                Server server = null;

                while (server == null) {
                    if (Thread.interrupted()) {
                        return null;
                    }
                    List<Server> upList = lb.getReachableServers(); //当前存活的服务
                    List<Server> allList = lb.getAllServers();  //获取全部的服务

                    int serverCount = allList.size();
                    if (serverCount == 0) {
                        return null;
                    }

                    //int index = rand.nextInt(serverCount);
                    //server = upList.get(index);
                    if (total < 5) {
                        server = upList.get(currentIndex);
                        total++;
                    } else {
                        total = 0;
                        currentIndex++;
                        if (currentIndex >= upList.size()) {
                            currentIndex = 0;
                        }
                    }

                    if (server == null) {
                        Thread.yield();
                        continue;
                    }

                    if (server.isAlive()) {
                        return (server);
                    }

                    server = null;
                    Thread.yield();
                }
                return server;
            }
        };
    }

}
