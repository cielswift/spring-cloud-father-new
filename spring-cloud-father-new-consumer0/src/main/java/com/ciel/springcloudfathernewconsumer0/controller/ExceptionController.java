package com.ciel.springcloudfathernewconsumer0.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 统一异常处理类
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     *  针对所有controller
     */
    @InitBinder
    public void init(WebDataBinder binder,HttpServletRequest request){
        System.out.println("针对所有controller 0000000000000000000000参数初始化");
    }

    @ExceptionHandler(Exception.class)
    public Object error(Exception e) {

        return Map.of("MSG","发生了异常 ----------CONSUMER////");
    }
}
