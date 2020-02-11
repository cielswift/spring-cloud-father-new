package com.ciel.springcloudconsulslave1.controller;

import com.ciel.springcloudconsulslave1.feign.FeignForProducer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    /**
     * 获取已经注册的服务
     */
    @Autowired
    protected DiscoveryClient discoveryClient;

    /**
     * 服务
     */
    @Autowired
    protected RestTemplate restTemplate;

    /**
     *feign调用
     */
    @Autowired
    protected FeignForProducer feignForProducer;


    @GetMapping("/")
    public Object index(){

        return Map.of("MSG","WELCOME - SLAVE1");
    }



    @GetMapping("/rpc")
    public Object rpc(){

        ServiceInstance instance = lunXun();

        String list = instance.getUri().toString().concat("/producer/list"); //资源地址

        String result = restTemplate.getForObject(list, String.class);

        return result;
    }


    @HystrixCommand(fallbackMethod = "fallback" ) //调用失败的 兜底方法
    @GetMapping("/flower")
    public Object flower(){
        return feignForProducer.flower();
    }

    public Object fallback(){
        return List.of(Map.of("---","不好意思"),
                Map.of("+++","服务器出错"));
    }

    @GetMapping("/water")
    public Object water(){
      return  feignForProducer.water();
    }


    volatile int value = 0;

    protected ServiceInstance lunXun(){ //轮询

        List<String> services = discoveryClient.getServices(); //获取服务列表

        List<ServiceInstance> instances =
                discoveryClient.getInstances("consul-producer"); //获取某个服务的集群

        ServiceInstance instance = null;

        synchronized (getClass()) {
            if(value>=instances.size()){
               value=0;
            }
            return instances.get(value++);
        }
    }
}
