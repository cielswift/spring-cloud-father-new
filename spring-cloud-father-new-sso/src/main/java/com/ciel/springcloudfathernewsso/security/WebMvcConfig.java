package com.ciel.springcloudfathernewsso.security;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.servlet.config.annotation.*;
import java.util.*;

@Configuration
//@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer { //或者继承WebMvcConfigurationSupport

    @Bean
    public FormContentFilter formContentFilter() { //注册rest风格url
        return new FormContentFilter();
    }


//    @Override  //注册拦截器
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new InterceptorCiel()).addPathPatterns("/**").excludePathPatterns("/error/page/**");
//        //拦截的地址和排除的地址
//    }


    //跨域设置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  //允许所有跨域请求
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    /**-------------------druid数据源监控----------------------*/
    @Bean
    @Lazy
    public ServletRegistrationBean servletRegistrationBean2(){
        ServletRegistrationBean slb = new ServletRegistrationBean();
        slb.setServlet(new StatViewServlet());
        slb.setUrlMappings(Arrays.asList(new String[]{"/druid/*"}));

        Map<String,String> initParam = new HashMap<>();
        initParam.put("loginUsername","ciel");
        initParam.put("loginPassword","c");

        //白名单：
        initParam.put("allow","127.0.0.1");

        //initParam.put("deny","127.0.0.1");

        slb.setInitParameters(initParam);
        return slb;
    }

    @Bean
    @Lazy
    public FilterRegistrationBean filterRegistrationBean2(){
        FilterRegistrationBean frb =new FilterRegistrationBean();
        frb.setFilter(new WebStatFilter());
        frb.setUrlPatterns(Arrays.asList(new String[]{"/*"}));

        Map<String,String> initParam = new HashMap<>();
        initParam.put("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        frb.setInitParameters(initParam);
        return frb;
    }

}
