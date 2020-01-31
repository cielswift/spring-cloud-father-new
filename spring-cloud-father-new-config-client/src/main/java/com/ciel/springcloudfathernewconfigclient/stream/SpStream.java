package com.ciel.springcloudfathernewconfigclient.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @EnableBinding 注解接收一个或者多个接口类型的参数 Spring Cloud Stream提供了Source(输出)、Sink(输入)和Process(输入输出)接口
 * Spring Cloud Stream会创建接口的实现  @Autowired private Sink sink; 直接使用
 */
@EnableBinding(Sink.class)
public class SpStream {


    @Autowired //前面通过Sink注入了,这里直接使用
    private Sink sink;

    @Autowired
    @Qualifier("input") //通道也可以直接使用
    private SubscribableChannel subscribableChannel;


    @Scheduled(cron = "1/2 * * * * ?")
    public void aa(){
        SubscribableChannel input = sink.input();
        input.send(new Msg("XIAPEIXIN202"));
    }

    /**
     *@StreamListener 注解到一个方法上，这个方法会接收到Stream处理事件
     */
    @StreamListener(Sink.INPUT)
    public void processVote(Object vote) {
        System.out.println(vote);
    }

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * spring的上下文对象,可以直接获取bean
     */
    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;
}
