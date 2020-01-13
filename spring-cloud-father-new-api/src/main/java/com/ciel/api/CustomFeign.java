package com.ciel.api;

import com.ciel.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/feign")
public interface CustomFeign {

    @GetMapping("/list")
    public List<User> list();

    /**
     *  GET 提交方式
     * //feign传参必须要加@RequestParam注解 ,不能传递pojo,只能单个传递
     *
     */
    @GetMapping("/list2")
    public List<User> list2(@RequestParam("name") String name,@RequestParam("sex") Integer age);

    /**
     *Post方式可以传递对象需要使用@RequestBody
     * @return
     */

    @PostMapping("/list3")
    public List<User> list3(@RequestBody User user);


}
