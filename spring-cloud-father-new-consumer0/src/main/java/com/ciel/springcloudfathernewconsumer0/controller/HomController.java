package com.ciel.springcloudfathernewconsumer0.controller;

import com.ciel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class HomController {

    @Autowired
    private LoadBalancerClient balancerClient; //ribbon 负载均衡器

    @Autowired
    protected RestTemplate restTemplate;

    @GetMapping("/home")
    public List<User> home(){

        //选择调用的服务的名称
        //ServiceInstance 封装了服务的基本信息，如 IP，端口
        ServiceInstance si = balancerClient.choose("SPRINGCLOUD-PRODUCER");

        String url="http://"+si.getHost()+":"+si.getPort()+"/producer/user/list";

        //参数化类型引用
        ParameterizedTypeReference<List<User>> responseType=
                new ParameterizedTypeReference<List<User>>() { };

        //ResponseEntity:封装了返回值信息
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null ,responseType);
                                    //路径 方式, 参数, 返回值用什么包装

        List<User> list=responseEntity.getBody();
        return list;

    }
}
