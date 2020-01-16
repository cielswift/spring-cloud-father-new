package com.ciel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 交易
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Trade extends EntityFather {


    /**
     * 订单 ID
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     * 用户 ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 支付价
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 1 未付款 2 付款中 3 付款失败 4 付款完成
     */
    @TableField("PAY_STATUS")
    private Integer payStatus;

    /**
     * 支付类型:1-支付宝支付， 2-网银在线，3-银联，4-微信支付
     */
    @TableField("PAY_TYPE")
    private Integer payType;

    /**
     * 网关支 付流水号
     */
    @TableField("GATEWAY_PAY_NUM")
    private String gatewayPayNum;

    /**
     * 网关支付 时间
     */
    @TableField("GATEWAY_PAY_TIME")
    private LocalDateTime gatewayPayTime;

    /**
     * 网关 实际支付金额
     */
    @TableField("GATEWAY_PAY_PRICE")
    private BigDecimal gatewayPayPrice;


}
