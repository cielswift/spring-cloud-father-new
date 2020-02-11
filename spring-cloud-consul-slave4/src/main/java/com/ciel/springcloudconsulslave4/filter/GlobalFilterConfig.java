package com.ciel.springcloudconsulslave4.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
public class GlobalFilterConfig {

    /**
     * 全局过滤器
     */

    @Bean
    @Order(-1)
    public GlobalFilter a() {
        return (exchange, chain) -> {

            System.out.println("start  -PRE -PRE -PRE");
            exchange.getRequest();

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                System.out.println("chain -POST -POST -POST");
            }));
        };
    }


    @Bean
    @Order(0)
    public GlobalFilter b() {

        return (exchange, chain) -> {

            String token = exchange.getRequest().getQueryParams().getFirst("authToken");
            //返回401状态码和提示信息
            if (!StringUtils.isBlank(token)) {
                ServerHttpResponse response = exchange.getResponse();
                JSONObject message = new JSONObject();
                message.put("status", -1);
                message.put("data", "鉴权失败");
                byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                //指定编码，否则在浏览器中会中文乱码
                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }

            
            //重定向(redirect)到登录页面
            if (!StringUtils.isBlank(token)) {
                String url = "http://想跳转的网址";
                ServerHttpResponse response = exchange.getResponse();
                //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().set(HttpHeaders.LOCATION, url);
                return response.setComplete();
            }


            System.out.println("second pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                System.out.println("second post filter");

            }));
        };

    }


}
