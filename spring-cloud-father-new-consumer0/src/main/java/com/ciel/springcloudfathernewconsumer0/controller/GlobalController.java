package com.ciel.springcloudfathernewconsumer0.controller;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 全局异常处理 ,统一数据响应格式
 */
@ControllerAdvice(annotations = RestController.class ) //支持resultController
public class GlobalController implements ResponseBodyAdvice<Object> {

    /**
     *  针对所有controller
     */
    @InitBinder
    public void init(WebDataBinder binder,HttpServletRequest request){
        System.out.println("针对所有controller 参数初始化");
    }


    @ExceptionHandler({Exception.class}) //可以处理什么异常;
    public Object mav(Exception e, HttpServletRequest request) {

       return Map.of("MSG","发生了异常 CONSUMER////");
    }

    /**
     *支持什么样的返回格式, 这里支持resultController
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object response, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        return Map.of("MSG","统一返回格式添加","BODY",response.toString());
    }
}
