package com.ciel.springcloudfathernewsso.security.filter;

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
public class RemainsFilter extends AbstractAuthenticationProcessingFilter {

    protected RemainsFilter(String defaultFilterProcessesUrl,AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }


    /**
     * 进行身份校验处理
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp)
            throws AuthenticationException, IOException, ServletException {

        User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
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
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp,
                                              AuthenticationException failed) throws IOException, ServletException {


    }
}
