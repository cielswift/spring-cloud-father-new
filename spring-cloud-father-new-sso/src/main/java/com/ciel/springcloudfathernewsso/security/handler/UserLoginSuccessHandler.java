package com.ciel.springcloudfathernewsso.security.handler;

import com.ciel.api.IUserService;
import com.ciel.springcloudfathernewsso.security.jwt.JwtUtil;
import com.ciel.springcloudfathernewsso.security.jwt.ResultUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description 登录成功处理类
 * @Author Sans
 * @CreateTime 2019/10/3 9:13
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    @Qualifier("redisTemplateString")
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    /**
     * 登录成功返回结果
     * @Author Sans
     * @CreateTime 2019/10/3 9:27
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws JsonProcessingException {


        //Principal 主体，存放了登录用户的信息
        User user = (User)authentication.getPrincipal();
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

//        String callback = request.getParameter("callback");
//        response.sendRedirect(callback);

    }
}