package com.ciel.springcloudfathernewproducer1.controller.feign;

import com.ciel.api.CustomFeignHystrixFactory;
import com.ciel.api.IUserService;
import com.ciel.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CustomFeignHystrixFactoryController implements CustomFeignHystrixFactory {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;

    @Override
    public List<User> fhf() throws InterruptedException {

        logger.warn("模拟大量数据查询慢----------------------------");
        Thread.sleep(2000);


        List<User> list = userService.list();

        User user = new User();
        user.setName("1 ++++++号生产者");
        user.setPrice(new BigDecimal("2560.58"));
        list.add(user);

        return list;
    }

}
