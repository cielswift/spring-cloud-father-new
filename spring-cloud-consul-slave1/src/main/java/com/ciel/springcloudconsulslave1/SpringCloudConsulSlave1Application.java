package com.ciel.springcloudconsulslave1;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient //使用consul或者zookeeper,nacos作为注册中心

@EnableFeignClients //开启feign

@EnableHystrix //hystrix熔断器

@EnableHystrixDashboard //熔断器面板

public class SpringCloudConsulSlave1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsulSlave1Application.class, args);
    }

    /**
     *  consul agent -dev -ui -node=ciel  -dev 开发模式 -node 节点名称 -ui开启界面
     *
     *  server： 以server身份启动。默认是client
     *  bootstrap-expect：集群要求的最少server数量，当低于这个数量，集群即失效。
     *  data-dir：data存放的目录，更多信息请参阅consul数据同步机制
     *  node：节点id，集群中的每个node必须有一个唯一的名称。默认情况下，Consul使用机器的hostname
     *  bind：监听的ip地址。默认绑定0.0.0.0，可以不指定。表示Consul监听的地址,而且它必须能够被集群中的其他节点访问。Consul默认会监听第一个private IP,但最好还是提供一个。生产设备上的服务器通常有好几个网卡，所以指定一个不会出错
     *  client: 客户端的ip地址，0.0.0.0是指谁都可以访问（不加这个，下面的ui :8500无法访问）
     *  ui: 可以访问UI界面
     * -config-dir指定配置文件夹，Consul会加载其中的所有文件
     * -datacenter 指定数据中心名称，默认是dc1
     *
     * consul agent -config-file=‪C:/ciel/consul_cluster/consul_1.6.3/config.json
     */

    @Bean
    //@LoadBalanced()  // 不需要这个
    public RestTemplate restTemplate() {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        //60s
        requestFactory.setConnectTimeout(60*1000); //超时时间
        requestFactory.setReadTimeout(60*1000); //超时时间


        RestTemplate restTemplate = new RestTemplate(requestFactory);
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
     * http://127.0.0.1:4010/slave1/hystrix 里输入  http://127.0.0.1:4010/slave1/actuator/hystrix.stream
     * 然后访问一个rpc url
     *
     *
     *开启监控必须添加这个bean
     */
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
