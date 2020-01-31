package com.ciel.springcloudfathernewwebflux.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
public class WebFluxController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/index")
    public Mono<Object> index() {

        //return Mono.just("hello webflux");

        logger.warn("当前线程名称"+Thread.currentThread().getName());

        return Mono.create(monoSink -> {
            logger.info("创建 Mono");

            monoSink.success("hello webflux");
        })
                .doOnSubscribe(subscription -> { //当订阅者去订阅发布者的时候，该方法会调用

                    logger.info("{}", subscription);
                    logger.warn("当前线程名称"+Thread.currentThread().getName());
                }).doOnNext(o -> {  //当订阅者收到数据时，改方法会调用

                    logger.info("{}", o);
                    logger.warn("当前线程名称"+Thread.currentThread().getName());
                });

    }

    /**
     * 发射多个数据
     * @return
     */
    @GetMapping("/flux")
    public Flux<Object> flux() {
        return Flux.just(Stream.generate(UUID::randomUUID).limit(10000).collect(Collectors.toList()));
    }

}
