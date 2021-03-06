package com.ciel.springcloudfathernewsso.security.filter;

import com.ciel.common.tokenSecurity.token.IpAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;

/**
 * 处理所有HTTP Request和Response对象，并将其封装成AuthenticationMananger可以处理的Authentication。
 * 并且在身份验证成功或失败之后将对应的行为转换为HTTP的Response。同时还要处理一些Web特有的资源比如Session和Cookie。
 * 总结成一句话，就是替AuthenticationMananger把所有和Authentication没关系的事情全部给包圆了
 */
public class IpAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public IpAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/ip_verify"));

        /**
         * 使用 /ip_verify 该端点进行 ip 认证,也就是拦截的地址;
         * UsernamePasswordAuthenticationFilter 拦截的是 super(new AntPathRequestMatcher("/login", "POST"));
         */

        setAuthenticationManager(authenticationManager);
    }


    /**
     * 进行ip身份校验处理
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        // 获取 host 信息
        String host = request.getRemoteHost();
        // 交给内部的 AuthenticationManager 去认证，实现解耦
        return getAuthenticationManager().authenticate(new IpAuthenticationToken(host));
    }


    /**
     * 成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {


    }

    /**
     *身份校验失败之后调用方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp,
                                              AuthenticationException failed) throws IOException, ServletException {


    }
}
