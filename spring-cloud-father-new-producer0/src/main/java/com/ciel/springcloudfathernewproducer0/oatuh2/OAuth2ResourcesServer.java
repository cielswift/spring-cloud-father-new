//package com.ciel.springcloudfathernewproducer0.oatuh2;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableResourceServer //整合oauth2
//public class OAuth2ResourcesServer extends ResourceServerConfigurerAdapter {
//
//    /**
//     *jdbc
//     * * ------------------------------------------------------------------
//     *       校验检查token
//     *
//     *      post http://127.0.0.1:3400/sso/oauth/check_token?token=fdc042f0-5a93-4dd5-be3e-5ef35d562d16
//     *       {
//     *           "aud": [
//     *               "springcloud-producer"
//     *          ],
//     *           "user_name": "xiapeixin",
//     *           "scope": [
//     *               "read",
//     *               "write"
//     *           ],
//     *           "active": true,
//     *          "exp": 1580625060,
//     *           "authorities": [
//     *               "ROLE_admin",   //权限
//     *               "sys_add"
//     *           ],
//     *           "client_id": "xiapeixin"
//     *       }
//     * 资源服务器
//     *
//     * get http://127.0.0.1:3200/producer/user/list
//     *
//     *  需要在header中带上Authorization = Bearer c48f286d-a65a-455b-9bf6-01d0757bf186  (token)
//     *  以及Authentication = eyJ0eXAiOiJKV.... (自定义的jwt,登录需要用到)
//     *
//     */
//
//    @Autowired
//    private DataSource  dataSource;
//    /**
//     * token 持久化策略
//     */
//    @Bean
//    public TokenStore tokenStore(){
//        return new JdbcTokenStore(dataSource);
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//
//        resources.resourceId("springcloud-producer") //资源id,数据库中配置的
//                .tokenStore(tokenStore()) //验证令牌服务
//                .stateless(true);
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//            .antMatchers("/login/**","/logout/**","/favicon.ico").permitAll()
//
//            .antMatchers(HttpMethod.GET,"/user/**").access("#oauth2.hasScope('read')")
//            .antMatchers(HttpMethod.POST,"/user/**").access("#oauth2.hasScope('write')")
//            .antMatchers(HttpMethod.PUT,"/user/**").access("#oauth2.hasScope('write')")
//            .antMatchers(HttpMethod.DELETE,"/user/**").access("#oauth2.hasScope('write')")
//            .antMatchers(HttpMethod.OPTIONS,"/user/**").access("#oauth2.hasScope('write')")
//            .and()
//            .csrf().disable() //关闭csrf
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //不记录session
//            .and()
//            .headers().addHeaderWriter((req,resp) -> {
//            resp.setHeader("Access-Control-Allow-Origin","*"); //允许跨域
//            if("OPTIONS".equals(req.getMethod())){ //如果是跨域的 预请求
//
//                resp.setHeader("Access-Control-Allow-Methods",req.getHeader("Access-Control-Request-Method"));
//                resp.setHeader("Access-Control-Allow-Headers",req.getHeader("Access-Control-Request-Headers"));
//            }
//
//        });
//    }
//
//    /**
//     * 解析
//     */
//    @Bean
//    public ResourceServerTokenServices tokenServices(){
//        RemoteTokenServices tokenServices = new RemoteTokenServices();
//        tokenServices.setCheckTokenEndpointUrl("http://127.0.0.1:3400/sso/oauth/check_token");
//        tokenServices.setClientId("xiapeixin");
//        tokenServices.setClientSecret("123456");
//        return tokenServices;
//    }
//}
