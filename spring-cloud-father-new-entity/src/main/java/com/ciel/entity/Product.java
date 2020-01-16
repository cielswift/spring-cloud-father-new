package com.ciel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 产品信息
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Product extends EntityFather {

    /**
     * 产品名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 产品状态：0 待 审，1 上架，2 下架，3 停售，4 测试
     */
    @TableField("STATUS")
    private Integer status;

    /**
     * 产品价格
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 产品详情
     */
    @TableField("DETAIL")
    private String detail;



}
