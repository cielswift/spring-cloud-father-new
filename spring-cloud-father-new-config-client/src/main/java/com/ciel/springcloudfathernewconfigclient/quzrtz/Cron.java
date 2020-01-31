package com.ciel.springcloudfathernewconfigclient.quzrtz;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cron {

    String value() default "0/5 * * * * ?";
}
