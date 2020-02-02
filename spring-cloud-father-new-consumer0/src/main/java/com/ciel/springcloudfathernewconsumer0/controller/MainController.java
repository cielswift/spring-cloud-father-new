package com.ciel.springcloudfathernewconsumer0.controller;

import com.ciel.entity.User;
import com.ciel.springcloudfathernewconsumer0.service.CusFeignHystrix;
import com.ciel.springcloudfathernewconsumer0.service.CusFeignHystrixFactory;
import com.ciel.springcloudfathernewconsumer0.service.CusUserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class MainController {

    /**
     * @InitBinder标注的方法, 方法入参和 @RequestMapping方法入参可选范围一样,比如HttpServletRequest、ModelMap这些;
     * 通常一个入参 WebDataBinder 就够我们使用了
     *
     * @InitBinder 标注的方法返回值, 必须为nul
     *
     * @InitBinder 标注的方法执行是多次的，一次请求来就执行一次。 用于对参数进行预修改;
     */
    @InitBinder
    public void init(WebDataBinder binder,HttpServletRequest request){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        System.out.println("+++++++++++++++++++++++++++++++");
    }

    /***
     * FEIGN HYSTRIX 调用演示==========================================
     */

    /**
     * fallbackFactory
     */
    @Autowired
    private CusFeignHystrixFactory cusFeignHystrixFactory;

    @GetMapping("/fhf")
    public Object fhf(@RequestParam(value = "name",required = false) String name) throws InterruptedException {

         // throw new RuntimeException("怕怕怕怕");

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
