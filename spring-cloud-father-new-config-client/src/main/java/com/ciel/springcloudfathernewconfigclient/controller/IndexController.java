package com.ciel.springcloudfathernewconfigclient.controller;

import com.ciel.springcloudfathernewconfigclient.bean.Xiapeixin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RefreshScope //开启更新功能 是为了可以动态刷新这个Controller的属性

/**
 *  修改git后使用
 *  curl -X POST http://localhost:3800/configclient/actuator/refresh
 *  刷新这个项目的配置
 *
 *  获取启动消息总线 使用curl -X POST http://localhost:3800/configclient/actuator/bus-refresh 刷新
 *  注: 只需要刷新一个配置中心客户端项目,集群的其他项目的配置文件也会被自动刷新
 *
 *  1 刷新指定服务  http://Config-Server/actuator/bus-refresh?destination=需要刷新的服务名称:端口
 *  2 刷新指定集群  http://Config-Server/actuator/bus-refresh?destination=需要刷新的服务名称:**
 *
 */
public class IndexController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Xiapeixin xiapeixin;

    @Value("${xiapeixin.name}")
    private String name;

    @GetMapping("/index")
    public Object index(HttpServletRequest request){

        redisTemplate.opsForValue().set(request.getRemoteHost(), LocalDateTime.now().toString());
        return Map.of("XXX",xiapeixin.getName());
    }

}
