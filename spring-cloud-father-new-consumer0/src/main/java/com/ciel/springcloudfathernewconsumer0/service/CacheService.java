package com.ciel.springcloudfathernewconsumer0.service;

import com.ciel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 缓存功能
 */
@Service
@CacheConfig(cacheNames= "my_cache_user_consumer",cacheManager = "cacheManagerJSON")
public class CacheService {

    @Autowired
    private RestTemplate restTemplate;

    //注意使用缓存时key的设置，使用字符串需要加单引号。#id表示应用参数中的id属性
    @Cacheable(key="'user_'+#id")
    public List<User> findUser(Integer id) {

        List<User> forObject = restTemplate.getForObject
                ("http://SPRINGCLOUD-PRODUCER/producer/role/rs",List.class);
        return forObject;
    }

    //删除缓存
    @CacheEvict(key="'user_'+#id")
    public void delUser(Integer id) {
        System.out.println("++++++DEL+++++++"+id);
    }

}
