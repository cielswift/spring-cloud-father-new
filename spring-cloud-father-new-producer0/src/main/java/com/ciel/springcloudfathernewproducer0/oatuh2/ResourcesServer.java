//package com.ciel.springcloudfathernewproducer0.oatuh2;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableResourceServer //整合oauth2
//public class ResourcesServer extends ResourceServerConfigurerAdapter {
//
//    @Autowired
//    @Lazy
//    private DataSource  dataSource;
//    /**
//     * token 持久化策略
//     */
//    @Bean
//    @Primary
//    public TokenStore tokenStore(){
//        TokenStore mysqlD = new JdbcTokenStore(dataSource);
//        return mysqlD;
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        /**
//         * yml中配置的服务名称
//         */
//        resources.resourceId("springcloud-producer").tokenStore(tokenStore());
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
////            .requestMatchers()
////            // 保险起见，防止被主过滤器链路拦截
////          .antMatchers("/account/**").and()
////            .authorizeRequests().anyRequest().authenticated()
////             .and()
//             .authorizeRequests()
//             .antMatchers(HttpMethod.GET,"/**").access("#oauth2.hasScope('read')")
//           .antMatchers(HttpMethod.POST,"/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PUT,"/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.DELETE,"/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.OPTIONS,"/**").access("#oauth2.hasScope('write')")
//                .and()
//                .headers().addHeaderWriter((req,resp) -> {
//                resp.setHeader("Access-Control-Allow-Origin","*"); //允许跨域
//                if("OPTIONS".equals(req.getMethod())){ //如果是跨域的 预请求
//
//                    resp.setHeader("Access-Control-Allow-Methods",req.getHeader("Access-Control-Request-Method"));
//                    resp.setHeader("Access-Control-Allow-Headers",req.getHeader("Access-Control-Request-Headers"));
//                }
//
//        });
//    }
//}
