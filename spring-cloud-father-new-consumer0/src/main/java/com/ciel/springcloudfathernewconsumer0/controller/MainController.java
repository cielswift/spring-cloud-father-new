package com.ciel.springcloudfathernewconsumer0.controller;

import com.ciel.entity.User;
import com.ciel.springcloudfathernewconsumer0.service.CusFeignHystrix;
import com.ciel.springcloudfathernewconsumer0.service.CusFeignHystrixFactory;
import com.ciel.springcloudfathernewconsumer0.service.CusUserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MainController {

    /***
     * FEIGN HYSTRIX 调用演示==========================================
     */

    /**
     * fallbackFactory
     */
    @Autowired
    private CusFeignHystrixFactory cusFeignHystrixFactory;

    @GetMapping("/fhf")
    public Object fhf(){
        return  cusFeignHystrixFactory.fhf();
    }

    /**
     * fallback
     */
    @Autowired
    private CusFeignHystrix cusFeignHystrix;

    @GetMapping("/feihys")
    public Object feignHystrix(){

        return cusFeignHystrix.usl();
    }


    /***
     * FEIGN 调用演示===================================================
     */

    @Autowired
    private CusUserFeign cusUserFeign;

    @GetMapping("/main")
    public Object main(String name) {

        return cusUserFeign.list2(name, 24);
    }


    @GetMapping("/fuck")
    public Object fuck() {
        User user = new User();
        user.setName("xiapeixin");
        user.setPrice(new BigDecimal("499.98"));

        List<User> users = cusUserFeign.list3(user);

        return users;
    }

    @GetMapping("/add")
    public Object add() {
        User user = new User();
        user.setName("xiapeixin");
        user.setPrice(new BigDecimal("499.98"));

        List<User> users = cusUserFeign.add3(user);

        return users;
    }

}
