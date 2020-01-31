package com.ciel.springcloudfathernewproducer0.filter;

import com.ciel.api.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CusFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return  super.attemptAuthentication(request,response);
    }


    /**
     *优先执行
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        response.getWriter().println("ffffffffffffffffff");
        response.getWriter().close();
       // response.sendRedirect("http://127.0.0.1:3200/producer/oauth/authorize?response_type=code&client_id=xiapeixin_one");

        //.successHandler(new AuthenticationSuccessHandler() {
//@Override
//public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//        response.sendRedirect("http://127.0.0.1:3200/producer/oauth/authorize?response_type=code&client_id=xiapeixin_one");
//        }
//        })
    }

}
