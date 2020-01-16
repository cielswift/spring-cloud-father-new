package com.ciel.springcloudfathernewconsumer0.fallback;

import com.ciel.entity.User;
import com.ciel.springcloudfathernewconsumer0.service.CusFeignHystrixFactory;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * fallbackFactory,都是实现断路器的功能;泛型是@FeignClient标记的类
 */
@Component
public class FeignHystrixFallbackFactory implements FallbackFactory<CusFeignHystrixFactory> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public CusFeignHystrixFactory create(Throwable throwable) {

        return new CusFeignHystrixFactory(){
            @Override
            public List<User> fhf() {

                logger.error("异常信息::::::"+throwable.getMessage());

                User temp = new User();
                temp.setName("<<<<<<<<<<<<<<<<<<服务调用失败,这是兜底方法>>>>>>>>>>>>>>>>>>");
                return List.of(temp);
            }
        };
    }

}
