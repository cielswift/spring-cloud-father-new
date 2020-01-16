package com.ciel.springcloudfathernewproducer0.controller.feign;

import com.ciel.api.CustomFeignHystrixFactory;
import com.ciel.api.IUserService;
import com.ciel.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class CustomFeignHystrixFactoryController implements CustomFeignHystrixFactory {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;

    @Override
    public List<User> fhf() {
        logger.warn("++++++++++++++++++++++++");
        return userService.list();
    }
}
