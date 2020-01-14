package com.ciel.springcloudfathernewconsumer0.fallback;

import com.ciel.entity.User;
import com.ciel.springcloudfathernewconsumer0.service.CusFeignHystrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 兜底类实现@FeignClient标记的接口  ,必须加@Component,被spring管理;
 */
@Component
public class FeignHystrixFallback implements CusFeignHystrix {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<User> usl() {
        logger.error("服务调用失败, 这是兜底 方法00000000000000");
        User temp = new User();
        temp.setName("MGSSSSS<><><><><><*****$$$$$$$$$SSS返回托底数据");
        return List.of(temp);
    }

}
