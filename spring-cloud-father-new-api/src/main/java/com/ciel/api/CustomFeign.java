package com.ciel.api;

import com.ciel.entity.User;
import org.springframework.http.MediaType;
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

    /**
     * 使用HTTPClient提升连接性能,get方式
     * @return
     */
    @GetMapping(value="/add3",consumes= MediaType.APPLICATION_JSON_VALUE)
    public List<User> add3(@RequestBody User user);

}
