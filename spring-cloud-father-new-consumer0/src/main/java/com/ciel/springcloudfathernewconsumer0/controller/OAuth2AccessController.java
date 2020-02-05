package com.ciel.springcloudfathernewconsumer0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OAuth2AccessController {

    @Autowired
    private RestTemplate restTemplate;

    /** 请求获取资源
     */
    @GetMapping("/request")
    public String requestResources(HttpServletRequest request){

        String token = request.getParameter("Authentication");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication", token);

        Map<String,String> param = new HashMap<>();
        param.put("client_id","xiapeixin");
        param.put("response_type","code");
        param.put("redirect_uri","http://127.0.0.1:3300/consumer/foll");

        HttpEntity<String> formEntity = new HttpEntity<String>(headers);

        String forObject = restTemplate.postForObject
                ("http://SPRINGCLOUD-SSO/sso/oauth/authorize?client_id={client_id}" +
                        "&response_type={response_type}&redirect_uri={redirect_uri}",
                        formEntity,String.class,param);
        return forObject;

    }
    /**
     * 回调,并获取token
     */
    @RequestMapping("/foll")
    public Object foll(HttpServletRequest request){

        String code = request.getParameter("code");

        String token = request.getParameter("Authentication");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication", token);

        Map<String,String> param = new HashMap<>();
        param.put("client_id","xiapeixin");
        param.put("grant_type","authorization_code");
        param.put("client_secret","123456");
        param.put("code",code);
        param.put("redirect_uri","http://127.0.0.1:3300/consumer/foll");

        HttpEntity<String> formEntity = new HttpEntity<String>(headers);

        String forObject = restTemplate.postForObject
                ("http://SPRINGCLOUD-SSO/sso/oauth/token?client_id={client_id}" +
                        "&grant_type={authorization_code}&client_secret={123456}" +
                        "&code={code}&redirect_uri={redirect_uri}",formEntity,String.class,param);

        return forObject;
    }
}
