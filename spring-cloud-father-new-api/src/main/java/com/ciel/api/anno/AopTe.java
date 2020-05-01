package com.ciel.api.anno;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AopTe {

   Class<? extends Throwable> rollback () default Exception.class;

}
