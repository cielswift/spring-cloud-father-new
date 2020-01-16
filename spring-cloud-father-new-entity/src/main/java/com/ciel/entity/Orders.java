package com.ciel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Orders extends EntityFather {

    /**
     * 产品 ID
     */
    @TableField("PRODUCT_ID")
    private Long productId;

    /**
     * 价格
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 用户账号 ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 交易号 ID
     */
    @TableField("TRADE_ID")
    private Long tradeId;

    /**
     * 支付状态 0=未支付 1=已支付
     */
    @TableField("TRADE_STATUS")
    private Integer tradeStatus;



}
