package com.ciel.springcloudfathernewturbines;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication

@EnableEurekaClient //服务注册eureka ,表示可以被Eureka注册中心发现
@EnableAspectJAutoProxy


@EnableTurbine //监控多个服务
//@EnableTurbineStream //更换这个,用于启动TurbineMQ

public class SpringCloudFatherNewTurbinesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewTurbinesApplication.class, args);
    }

    /**
     * 被Turbine监控的服务需要 如consumer,provider 需先引入
     *      spring-cloud-starter-netflix-hystrix,
     *      spring-boot-starter-actuator,
     *      spring-cloud-starter-netflix-hystrix-dashboard -->
     *   并且在启动类添加@EnableHystrix ,@EnableHystrixDashboard 注解, 以及添加 @Bean
     *     public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet()......
     */


    /**Hystrix-dashboard是一款针对 Hystrix 进行实时监控的工具，
     * 通过HystrixDashboard我们可以在直观地看到各HystrixCommand 的请求响应时间, 请求成功率等数据
     *
     */

    //先调用一个服务,然后访问http://127.0.0.1:3300/consumer/actuator/hystrix.stream

    //访问http://127.0.0.1:3300/consumer/hystrix  可视化界面
            //输入http://127.0.0.1:3300/consumer/actuator/hystrix.stream  或者 http://127.0.0.1:3500/turbines/turbine.stream

    //访问http://127.0.0.1:3500/turbines/turbine.stream 监控多个服务

    // SpringBoot2.0以后，不提供 hystrix.stream节点，需要自己增加

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
