package com.ciel.springcloudconsulslave1.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "person")
@Data
public class Me {

    protected String name;

    protected Integer age;


}
