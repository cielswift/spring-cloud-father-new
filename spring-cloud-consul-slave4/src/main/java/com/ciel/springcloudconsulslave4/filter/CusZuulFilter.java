//package com.ciel.springcloudconsulslave4.filter;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.exception.ZuulException;
//import org.springframework.stereotype.Component;
//
//import java.sql.SQLOutput;
//
//@Component
//public class CusZuulFilter extends ZuulFilter {
//
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return 0;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//
//        System.out.println("<<<<<<<<<<<<<<<<<<<<<<");
//
//        return null;
//    }
//}