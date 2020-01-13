package com.ciel.springcloudfathernewconsumer0.controller;

import com.ciel.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/roles")
    public Object roles() throws JsonProcessingException {

//        String forObject = restTemplate.postForObject
//                ("http://SPRINGCLOUD-PRODUCER/producer/role/roles",null,String.class);
//        return forObject;

        /** 带参数----------------------------------------------------------------------------*/
        Map<String,String> param = new HashMap<>();
        param.put("id","2");
        param.put("name","夏培鑫");
        param.put("r0name","职员");

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
