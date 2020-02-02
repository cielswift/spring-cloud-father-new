package com.ciel.springcloudfathernewproducer0.security;

import com.ciel.api.IUserService;
import com.ciel.springcloudfathernewproducer0.security.filter.JWTAuthenticationTokenFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringSecurity配置类
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * spring security的角色继承
     * ROLE_dba具备 ROLE_admin的所有权限，而 ROLE_admin则具备 ROLE_user的所有权限，继承与继承之间用一个空格隔开
     * @return
     */

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_dba > ROLE_admin \n ROLE_admin > ROLE_user";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }


    @Override //不经过security的过滤器
    public void configure(WebSecurity web) throws Exception {
       // web.ignoring().antMatchers("/**");
    }


    /**
     * 配置security的控制逻辑
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 不进行权限验证的请求或资源(从配置文件中读取)
                .antMatchers("/login/**","/logout/**","/favicon.ico").permitAll()
                // 其他的需要登陆后才能访问
                .anyRequest().authenticated()
                .and()
                // 配置未登录自定义处理类
                // 配置登录地址
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                // 配置登录成功自定义处理类
                .and()
                // 配置登出地址
                .logout()
                .logoutUrl("/logout")
                // 配置用户登出自定义处理类
                .and()
                // 配置没有权限自定义处理类
                // 开启跨域
                .cors()
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable();

        /**
         * 在用户未获认证的时候去请求一个需要认证后才能请求的数据，此时不给用户重定向，
         * 而是直接就返回一个 JSON，告诉用户这个请求需要认证之后才能发起，就不会有上面的事情了
         */
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {

                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                //未登录异常
                Map<String, Object> resultData = new HashMap<>();
                resultData.put("code", "5000");
                resultData.put("msg", "访问失败!::"+authException.getMessage());

                out.write(new ObjectMapper().writeValueAsString(resultData));
                out.flush();
                out.close();

            }
        });


        // 基于Token不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT过滤器
        http.addFilter(jwtAuthenticationTokenFilter());


    }

    @Autowired
    @Qualifier("redisTemplateString")
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @Bean
    public JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JWTAuthenticationTokenFilter tokenFilter = new JWTAuthenticationTokenFilter(authenticationManager());
        tokenFilter.setRedisTemplate(redisTemplate);
        tokenFilter.setUserService(userService);
        return tokenFilter;
    }

}
