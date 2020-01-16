package com.ciel.springcloudfathernewproducer1.controller;

import com.ciel.api.ITOrderService;
import com.ciel.api.IUserService;
import com.ciel.entity.TOrder;
import com.ciel.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

   protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private ITOrderService itOrderService;

    @GetMapping("/fkfb")
    public Object fkfb(){

        TOrder tOrder = new TOrder();
        tOrder.setName("aaaaaaaaaa");
        tOrder.setType("bbbbbbbbb");
        tOrder.setGmtCreate(LocalDateTime.now());

        itOrderService.save(tOrder);

        return Map.of("ok","+++++++++++++++++++++");
    }

    @GetMapping("/list")
    public List<User> list(){
        List<User> list = userService.list();
        User user = new User();
        user.setName("生产者1号提供服务");
        list.add(user);
        return list;
    }

    @GetMapping("/add")
    public Map add(){
        User user = new User();
        user.setName("xiapeixin");
        user.setPrice(new BigDecimal("2560.58"));
        userService.save(user);

      return Map.of("String","Integer");
    }

    @GetMapping("/transac")
    public Map transac(){

        User send = userService.getById(1215503130926424066L);
        User receive = userService.getById(1215503056011960322L);

        try {

            userService.transfer(send,receive,"200.00");
        }catch (Exception e){
            logger.error("交易失败");
        }

        return Map.of("String","Integer");
    }


    @GetMapping("/change")
    public Map change(){

        User user = userService.getById(1215503130926424066L);

        user.setName("夏培鑫202");

        if(userService.updateById(user)){
            logger.info("更新成功");
            return Map.of("String","更新成功");
        }else{
            logger.info("更新失败");
            return Map.of("String","更新失败");
        }
    }

}
