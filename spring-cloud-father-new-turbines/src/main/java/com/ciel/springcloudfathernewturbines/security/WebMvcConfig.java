package com.ciel.springcloudfathernewturbines.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

}
