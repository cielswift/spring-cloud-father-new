package com.ciel.springcloudconsulslave1.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data

/**
 * value：类名
 * description：描述
 */
@ApiModel

public class Emp implements Serializable {

    /**
     * @ApiModelProperty()
     * name：属性名称
     * value：描述
     * dataType：属性的数据类型
     * required：是否必填
     * example：举例说明
     * hidden：在文档上是否不可见
     */
    @ApiModelProperty(value = "员工工资", name = "price", required = false)
    private BigDecimal price;

    @ApiModelProperty(value = "员工年龄", name = "age", required = true,dataType= "Integer")
    private Integer age;

    @ApiModelProperty(value = "员工姓名", name = "name", required = true)
    private String name;

    @ApiModelProperty(value = "员工入职日期", name = "create", required = false)
    private LocalDateTime create;

   // *@ApiIgnore
    //可以修饰类或者非法，然后在文档中不再显示
}
