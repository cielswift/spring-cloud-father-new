package com.ciel.springcloudfathernewconfigclient.controller;

import com.ciel.springcloudfathernewconfigclient.bean.Xiapeixin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RefreshScope //开启更新功能 是为了可以动态刷新这个Controller的属性

/**
 *  修改git后使用
 *  curl -X POST http://localhost:3800/configclient/actuator/refresh
 *  刷新这个项目的配置
 */
public class IndexController {

    @Autowired
    private Xiapeixin xiapeixin;

    @Value("${xiapeixin.name}")
    private String name;

    @GetMapping("/index")
    public Object index(){
        return Map.of("XXX",xiapeixin.getName());
    }

}
