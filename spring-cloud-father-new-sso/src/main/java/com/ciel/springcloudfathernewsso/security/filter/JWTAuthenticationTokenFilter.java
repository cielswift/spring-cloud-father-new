package com.ciel.springcloudfathernewsso.security.filter;

import com.ciel.api.IUserService;
import com.ciel.common.tokenSecurity.token.CustomUser;
import com.ciel.common.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * JWT接口请求校验拦截器
 * 请求接口时会进入这里验证Token是否合法和过期
 *  是否需要刷新token
 */
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    private RedisTemplate redisTemplate;

    private IUserService userService;

    /**
     * token 刷新间隔 秒;
     */
    private final int refreshTime = 10800 ; //3小时

    /**
     *是否登录
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /**
         *  获取请求头中JWT的Token
         */
        String tokenHeader = request.getHeader("Authentication");

        /**
         * 从参数中获取token,以后删除
         */
        if(StringUtils.isEmpty(tokenHeader)){
            tokenHeader = request.getParameter("Authentication");
        }

        if (!StringUtils.isEmpty(tokenHeader)) {
            try {
                String token = JwtUtil.parseToken(tokenHeader);

                ObjectMapper format = new ObjectMapper();
                HashMap hashMap = format.readValue(token, HashMap.class);

                String redisToken =
                        (String)redisTemplate.opsForValue().get("USER_".concat(hashMap.get("username").toString()));

                if(!redisToken.equals(tokenHeader)){
                    throw new RuntimeException("找不到token");
                }

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

                /**
                 * 放入security 上下文中
                 */
                SecurityContextHolder.getContext().setAuthentication(authentication);

                /**
                 * 是否需要刷新token
                 */
                if(JwtUtil.isRefresh(tokenHeader,refreshTime)){

                    com.ciel.entity.User byUserName = userService.findByUserName(user.getUsername());

                    Map<String,Object> user_info = new HashMap<>();
                    user_info.put("username",user.getUsername());
                    user_info.put("authorites",user.getAuthorities());
                    user_info.put("id",byUserName.getId());
                    user_info.put("name",byUserName.getName());
                    user_info.put("password",byUserName.getPassword());

                    ObjectMapper format2 = new ObjectMapper();
                    String value2 = format2.writeValueAsString(user_info);

                    String token2 = JwtUtil.createToken(value2);
                    redisTemplate.opsForValue().set("USER_".concat(user.getUsername()),token2,JwtUtil.time, TimeUnit.SECONDS);

                    response.setHeader("Authentication",token2);
                }


            } catch (ExpiredJwtException e) {
                logger.info("Token过期");
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
        return;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}