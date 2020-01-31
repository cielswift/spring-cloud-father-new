package com.ciel.springcloudfathernewconfigclient.stream;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

public class Msg implements Message<String> {

    public String info;

    public Msg(String info){
        this.info=info;
    }

    public Msg(){

    }

    @Override
    public String getPayload() {
        return info;
    }

    @Override
    public MessageHeaders getHeaders() {
        return new MessageHeaders(Map.of("qoic","xiapeixin"));
    }

    @Override
    public String toString() {
        return "Msg{" +
                "info='" + info + '\'' +
                '}';
    }
}
