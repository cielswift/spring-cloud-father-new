package com.ciel.springcloudconsulslave1.controller;

import com.ciel.springcloudconsulslave1.entity.Me;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class NacosController {

    //要实现配置文件实时生效需要加注解:@RefreshScope

    /**
     * 上下文环境
     */
    @Autowired
    protected ConfigurableApplicationContext context;

    @Autowired
    protected Me me;

    @Value("${person.name}")  //主配置
    protected String name;

    @Value("${xia}")  //其他配置
    protected String xia;

    @Value("${xia2}") //其他配置2
    protected String xia2;

    @GetMapping("/conf")
    public Object conf(){

        /**
         * 通过上下文环境获取配置信息,实时动态更新
         */
        String property = context.getEnvironment().getProperty("person.name");

        return List.of(Map.of("---",me.getName()),
                Map.of("+++",me.getAge()));
    }

}
