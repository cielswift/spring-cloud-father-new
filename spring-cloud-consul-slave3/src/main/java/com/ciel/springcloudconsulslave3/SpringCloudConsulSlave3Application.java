package com.ciel.springcloudconsulslave3;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient

@EnableSwagger2Doc //开启整合springboot的swagger
public class SpringCloudConsulSlave3Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsulSlave3Application.class, args);
    }

    @Bean
    //@LoadBalanced()  //启用负载均衡机制
    public RestTemplate restTemplate() {
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
}
