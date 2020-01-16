package com.ciel.springcloudfathernewproducer0.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixDashboardConfig {


    /**Hystrix-dashboard是一款针对 Hystrix 进行实时监控的工具，
     * 通过HystrixDashboard我们可以在直观地看到各HystrixCommand 的请求响应时间, 请求成功率等数据
     *
     */

    //先调用一个服务,然后访问http://127.0.0.1:3300/consumer/actuator/hystrix.stream

    //访问http://127.0.0.1:3300/consumer/hystrix

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
