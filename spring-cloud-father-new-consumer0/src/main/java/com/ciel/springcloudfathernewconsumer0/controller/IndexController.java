package com.ciel.springcloudfathernewconsumer0.controller;

import com.ciel.entity.User;
import com.ciel.springcloudfathernewconsumer0.service.CacheService;
import com.ciel.springcloudfathernewconsumer0.service.HystrixMergeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class IndexController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    /** Hystrix======================================================================= */

    /**
     * 请求合并
     */
    @Autowired
    private HystrixMergeRequest hystrixMergeRequest;

    @GetMapping("/merge")
    public Object merge() throws ExecutionException, InterruptedException {

        Future<User> user = hystrixMergeRequest.getUser(1);
        Future<User> user1 = hystrixMergeRequest.getUser(2);
        Future<User> user2 = hystrixMergeRequest.getUser(3);

        logger.warn(user.get().toString());
        logger.warn(user1.get().toString());
        logger.warn(user2.get().toString());

        return Map.of("|||||||||||||||||||||||||||","oooooooooooooooooooooooooooooooooooooo");
    }

    /**
     * Hystrix 不支持第三方缓存; 使用springboot的缓存功能;
     */
    @Autowired
    private CacheService cacheService;

    @GetMapping("/cache")
    public Object cache(){
        return cacheService.findUser(1);
    }

    @GetMapping("/deca")
    public Object delCache(){
         cacheService.delUser(1);
         return Map.of("cache","夏培鑫");
    }

    /**
     * 服务降级
     * 以下四种情况将触发 fallback 调用
     * (1) 方法抛出非 HystrixBadRequestException 异常。
     * (2) 方法调用超时
     * (3) 熔断器开启拦截调用
     * (4) 线程池/队列/信号量是否跑满
      * @return
     */
    @GetMapping("/fall")
    @HystrixCommand(fallbackMethod="fallback")
    public Object fall(){
        String forObject = restTemplate.getForObject
                ("http://SPRINGCLOUD-PRODUCER/producer/role/rs",String.class);
        return forObject;
    }


    //返回返回托底数据的方法 //消费者端返回拖地数据
    public Object fallback() {
        return Map.of("MSG","服务调用失败,返回托底数据");
    }

    /** restTemplate 用法演示================================================================================================= */

    @GetMapping("/roles")
    public Object roles() throws JsonProcessingException {

        /** 带参数----------------------------------------------------------------------------*/
        Map<String,String> param = new HashMap<>();
        param.put("id","2");
        param.put("name","夏培鑫");
        param.put("r0name","社畜");

        String forObject = restTemplate.getForObject
                ("http://SPRINGCLOUD-PRODUCER/producer/role/roles?id={id}&name={name}&roles[0].name={r0name}",String.class,param);

        return forObject;

        /** json ----------------------------------------------------------------------------*/

//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//
//        User user = new User();
//        user.setId(99999999999L);
//        user.setName("夏培鑫");
//
//        String string = new ObjectMapper().writeValueAsString(user);
//
//        HttpEntity<String> formEntity = new HttpEntity<String>(string, headers);
//
//        String forObject = restTemplate.postForObject
//                ("http://SPRINGCLOUD-PRODUCER/producer/role/roles",formEntity,String.class);
//
//        return forObject;
    }

    @PostMapping("/file")
    public Object fielUpload(@RequestParam("fiel") MultipartFile file){


//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.setConnection("Keep-Alive");
//        headers.setCacheControl("no-cache");
//
//        FileSystemResource resource = new FileSystemResource(new File("C:/ciel/捕获.PNG"));
//
//        HttpEntity<FileSystemResource> httpEntity = new HttpEntity<>(resource);
//
//
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity("http://SPRINGCLOUD-PRODUCER/producer/role/file", httpEntity, String.class);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

        parts.add("file",file.getResource());

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parts, headers);

        ResponseEntity<Map> responseEntity =
                restTemplate.postForEntity("http://SPRINGCLOUD-PRODUCER/producer/role/file",httpEntity,Map.class);
        return responseEntity.getBody();
    }
}
