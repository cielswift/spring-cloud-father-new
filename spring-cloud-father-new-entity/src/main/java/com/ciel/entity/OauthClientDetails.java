package com.ciel.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author xiapeixin
 * @since 2020-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  主键; 标识每一个客户端
     */
    @TableId("client_id")
    private String clientId;

    /**
     * 可以访问的资源id
     */
    private String resourceIds;

    /**
     * 访问密钥
     */
    private String clientSecret;

    /**
     * 访问哪些资源的哪些权限,read,write
     */
    private String scope;

    /**
     * 授权类型
     */
    private String authorizedGrantTypes;

    /**
     * 回调,客户端的地址
     */
    private String webServerRedirectUri;

    /**
     * 访问权限
     */
    private String authorities;

    /**
     * 有效时间 token 过期时间 单位秒
     */
    private Integer accessTokenValidity;
    /**
     *
     */
    private Integer refreshTokenValidity;
    /**
     *预留字段
     */
    private String additionalInformation;
    /**
     *是否信任
     */
    private String autoapprove;


}
