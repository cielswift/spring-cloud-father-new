package com.ciel.springcloudfathernewzuul.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class ZuulGlobalController {

    @ExceptionHandler({Exception.class}) //可以处理什么异常;
    public Object mav(Exception e, HttpServletRequest request) {

        return Map.of("MSG","访问过于频繁////");
    }
}
