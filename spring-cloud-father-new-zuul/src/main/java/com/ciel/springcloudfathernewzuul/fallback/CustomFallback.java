package com.ciel.springcloudfathernewzuul.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 实现FallbackProvider接口，实现服务降级
 *
 * 1.添加@Component注解
 * 2.getRoute方法返回监控的服务名称
 * 3.FallbackProvider
 */
@Component
public class CustomFallback implements FallbackProvider {

    /**
     * 对指定的服务提供降级处理
     */
    @Override
    public String getRoute() {
        return "springcloud-consumer";
    }

    /**
     * 设置降级后返回的响应
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            /**
             * 响应头
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
            /**
             * 响应体
             */
            @Override
            public InputStream getBody() throws IOException {
                String content="请求服务不可达！请联系管理员！";
                return new ByteArrayInputStream(content.getBytes());
            }
            /**
             * 状态码：String类型
             */
            @Override
            public String getStatusText() throws IOException {
                return getStatusCode().getReasonPhrase();
            }
            /**
             * 状态码：HttpStatus类型
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }
            /**
             * 状态码：int类型
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return getStatusCode().value();
            }

            /**
             * 关闭
             */
            @Override
            public void close() {

            }
        };
    }

}
