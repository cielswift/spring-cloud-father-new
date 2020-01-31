package com.ciel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * @author xiapeixin
 * @since 2020-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Clientdetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("appId")
    private String appId;

    @TableField("resourceIds")
    private String resourceIds;

    @TableField("appSecret")
    private String appSecret;

    private String scope;

    @TableField("grantTypes")
    private String grantTypes;

    @TableField("redirectUrl")
    private String redirectUrl;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    @TableField("additionalInformation")
    private String additionalInformation;

    @TableField("autoApproveScopes")
    private String autoApproveScopes;


}
