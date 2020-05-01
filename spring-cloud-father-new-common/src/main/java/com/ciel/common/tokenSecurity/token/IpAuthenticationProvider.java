package com.ciel.common.tokenSecurity.token;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 自定义ip验证
 */

@Component
public class IpAuthenticationProvider implements AuthenticationProvider {

	final static Map<String, List<SimpleGrantedAuthority>> ipAuthorityMap = new HashMap<>();
    // 维护一个 ip 白名单列表，每个 ip 对应一定的权限
    
    static {
        ArrayList<SimpleGrantedAuthority> sga = new ArrayList<>();
        sga.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        sga.add(new SimpleGrantedAuthority("REMOVE"));
        ipAuthorityMap.put("127.0.0.1", sga);

        ArrayList<SimpleGrantedAuthority> sga2 = new ArrayList<>();
        sga2.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        sga2.add(new SimpleGrantedAuthority("ADD"));
        ipAuthorityMap.put("10.236.69.103", sga2);
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        IpAuthenticationToken ipAuthenticationToken = (IpAuthenticationToken) authentication;
        String ip = ipAuthenticationToken.getIp();
        List<SimpleGrantedAuthority> authorities = ipAuthorityMap.get(ip);

        if (authorities == null) {
            throw new BadCredentialsException("ip 不在白名单列表中");
        } else {
            // 封装权限信息，并且此时身份已经被认证
            return new IpAuthenticationToken(ip, authorities);
        }
    }

    // 只支持 IpAuthenticationToken 该身份
    @Override
    public boolean supports(Class<?> authentication) {
        return (IpAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}