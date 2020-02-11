package com.ciel.springcloudconsulslave1.feign;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ImplFallback implements FeignForProducer {

    /**
     * 优先级比@HystrixCommand 高
     * @return
     */
    @Override
    public Object flower() {
        return List.of(Map.of("---","远程服务器宕机了"),
                Map.of("+++","远程服务器宕机了"));
    }

    @Override
    public Object water() {
        return List.of(Map.of("---","远程服务器宕机了"),
                Map.of("+++","远程服务器宕机了"));
    }

}
