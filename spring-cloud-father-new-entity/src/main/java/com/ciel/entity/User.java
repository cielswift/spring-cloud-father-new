package com.ciel.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author xiapeixin
 * @since 2020-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends EntityFather {

    @TableField("NAME")
    private String name;

    @TableField(value = "VERSION",fill = FieldFill.INSERT)
    @Version  //乐观锁
    private Integer version;

    @TableField("PRICE")
    private BigDecimal price;

    @TableField("USERNAME")
    private String username;

    @TableField("PASSWORD")
    private String password;

    @TableField(exist = false)
    private List<Role> roles;
}
