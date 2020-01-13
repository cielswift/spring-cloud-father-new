package com.ciel.springcloudfathernewsso.security.filter;

import com.ciel.springcloudfathernewsso.security.jwt.JwtUtil;
import com.ciel.springcloudfathernewsso.security.token.CustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT接口请求校验拦截器
 * 请求接口时会进入这里验证Token是否合法和过期
 *
 * @Author Sans
 * @CreateTime 2019/10/5 16:41
 */
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中JWT的Token
        String tokenHeader = request.getHeader("Authentication");

        if (null != tokenHeader && !"".equals(tokenHeader.trim())) {

            try {
                String token = JwtUtil.parseToken(tokenHeader);

                ObjectMapper format = new ObjectMapper();
                HashMap hashMap = format.readValue(token, HashMap.class);

                List<Map> temp = (List<Map>) hashMap.get("authorites");

                List<GrantedAuthority> authorities = new ArrayList<>(temp.size());

                temp.forEach(t -> {
                    authorities.add(new SimpleGrantedAuthority(t.get("authority").toString()));
                });

                String username = (String) hashMap.get("username");
                Long id = (Long) hashMap.get("id");
                String name = (String) hashMap.get("name");
                String password = (String)hashMap.get("password");

                CustomUser user = new CustomUser(username,password,authorities);
                user.setName(name);
                user.setId(id);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, id, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                logger.info("Token过期");
            } catch (Exception e) {
                logger.info("Token无效");
            }
        }

        filterChain.doFilter(request, response);
        return;
    }
}