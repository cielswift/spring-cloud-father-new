package com.ciel.common.tokenSecurity.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * ip认证的token 类比UsernamePasswordAuthenticationToken
 */

public class IpAuthenticationToken extends AbstractAuthenticationToken {

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 这里设计的用意是模仿的 UsernamePasswordAuthenticationToken，第一个构造器是用于认证之前，
     * 传递给认证器使用的，所以只有 IP 地址，自然是未认证；
     * 第二个构造器用于认证成功之后，封装认证用户的信息，此时需要将权限也设置到其中，并且 setAuthenticated(true)。
     */

    public IpAuthenticationToken(String ip) {
        super(null);
        this.ip = ip;
        super.setAuthenticated(false);// 注意这个构造方法是认证时使用的
    }

    public IpAuthenticationToken(String ip, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.ip = ip;
        super.setAuthenticated(true);// 注意这个构造方法是认证成功后使用的
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.ip;
    }

}