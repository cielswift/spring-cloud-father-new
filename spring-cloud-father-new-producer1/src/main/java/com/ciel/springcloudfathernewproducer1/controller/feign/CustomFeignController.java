package com.ciel.springcloudfathernewproducer1.controller.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciel.api.CustomFeign;
import com.ciel.api.IUserService;
import com.ciel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * feign 无感知远程调用
 */

@RestController
public class CustomFeignController implements CustomFeign  {

    @Autowired
    private IUserService iUserService;

    @Override
    public List<User> list() {
        return iUserService.list();
    }

    @Override
    public List<User> list2(String name,Integer age) {
        return iUserService.list(new LambdaQueryWrapper<User>().eq(User::getName,name));
    }

    @Override
    public List<User> list3(User user) {
        return iUserService.list(new LambdaQueryWrapper<User>().eq(User::getName,user.getName()));
    }
}