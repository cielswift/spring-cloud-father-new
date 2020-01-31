package com.ciel.springcloudfathernewsso.security.filter;

import com.ciel.api.IUserService;
import com.ciel.springcloudfathernewsso.security.jwt.JwtUtil;
import com.ciel.springcloudfathernewsso.security.jwt.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 登录请求会走这个过滤器
     */


    public CustomAuthenticationFilter(){

        this.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                Map<String, Object> resultData = new HashMap<>();
                resultData.put("code", "2000");
                resultData.put("msg", "登录+1111111+成功");

                response.setHeader("Authentication", "token");

                ResultUtil.responseJson(response, resultData);
            }
        });

        this.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

                Map<String, Object> resultData = new HashMap<>();
                resultData.put("code", "3000");
                resultData.put("msg", "登录+++++00失败||".concat(exception.getMessage()));

                response.setHeader("Authentication", "token");

                ResultUtil.responseJson(response, resultData);
            }
        });
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       if(MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType())
            || MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())){

            ObjectMapper mapper = new ObjectMapper();

            UsernamePasswordAuthenticationToken authRequest = null;

            try (InputStream is = request.getInputStream()) {

                Map<String, String> authenticationBean = mapper.readValue(is, Map.class);

                authRequest =
                        new UsernamePasswordAuthenticationToken(authenticationBean.get("username"), authenticationBean.get("password"));
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            } finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } else {
            return super.attemptAuthentication(request, response);

        }
    }

    private RedisTemplate redisTemplate;

    private IUserService userService;


    /**
     *优先执行
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //Principal 主体，存放了登录用户的信息
        User user = (User)authResult.getPrincipal();
        System.out.println(user.getUsername());//admin
        System.out.println(user.getPassword());//密码输出为null
        System.out.println(user.getAuthorities());//

        com.ciel.entity.User byUserName = userService.findByUserName(user.getUsername());

        Map<String,Object> user_info = new HashMap<>();
        user_info.put("username",user.getUsername());
        user_info.put("authorites",user.getAuthorities());
        user_info.put("id",byUserName.getId());
        user_info.put("name",byUserName.getName());
        user_info.put("password",byUserName.getPassword());

        ObjectMapper format = new ObjectMapper();
        String value = format.writeValueAsString(user_info);

        String token = JwtUtil.createToken(value);
        redisTemplate.opsForValue().set("USER_".concat(user.getUsername()),token,1, TimeUnit.HOURS);

        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","200");
        resultData.put("msg", "登录成功");
        resultData.put("token",token);

        response.setHeader("Authentication",token);

        ResultUtil.responseJson(response,resultData);
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