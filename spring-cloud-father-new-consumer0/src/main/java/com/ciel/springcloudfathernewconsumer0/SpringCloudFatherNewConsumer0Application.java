package com.ciel.springcloudfathernewconsumer0;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
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
@EnableAspectJAutoProxy  //开启基于注解的aop

@EnableFeignClients //无感知远程调用
@EnableDiscoveryClient ////无感知远程调用
public class SpringCloudFatherNewConsumer0Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewConsumer0Application.class, args);
    }

    //restTemplate乱码问题处理,以及微服务间文件上传;自定义负载均衡器,feign无感知调用,404问题,传参问题处理;feign的压缩性能优化

    @Bean
    @LoadBalanced()  //启用负载均衡机制  不能和@Autowired private LoadBalancerClient balancerClient; 同时使用
    public RestTemplate restTemplate() { //服务调用者
        //   return new RestTemplate();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors()
                .add(new BasicAuthenticationInterceptor("xia", "123", StandardCharsets.UTF_8));

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
